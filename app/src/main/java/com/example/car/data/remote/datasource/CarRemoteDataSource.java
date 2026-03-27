package com.example.car.data.remote.datasource;

import androidx.annotation.NonNull;

import com.example.car.data.remote.api.CarApiService;
import com.example.car.data.remote.dto.CarDto;
import com.example.car.data.remote.dto.HealthDto;
import com.example.car.data.remote.dto.ImportDto;
import com.example.car.data.remote.error.ApiErrorParser;
import com.example.car.data.remote.error.ApiException;

import java.io.IOException;

import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.Nullable;

@Singleton
public final class CarRemoteDataSource {

    private static final String HEADER_DEMO_HINT = "X-Demo-Hint";

    private final CarApiService api;
    private final ApiErrorParser errorParser;

    @Inject
    public CarRemoteDataSource(@NonNull CarApiService api,
                               @NonNull ApiErrorParser errorParser) {
        this.api = api;
        this.errorParser = errorParser;
    }

    @NonNull
    public HealthDto health() throws IOException, ApiException {
        Response<HealthDto> resp = api.health().execute();
        if (resp.isSuccessful() && resp.body() != null) return resp.body();
        throw toApiException(resp);
    }

    @NonNull
    public ImportDto importCars(boolean force) throws IOException, ApiException {
        Response<ImportDto> resp = api.importCars(force).execute();
        if (resp.isSuccessful() && resp.body() != null) return resp.body();
        throw toApiException(resp);
    }

    @NonNull
    public CarsRemoteResult getCars(@NonNull String status,
                                    @NonNull String make,
                                    @NonNull String near,
                                    int limit,
                                    int offset) throws IOException, ApiException {
        Response<com.example.car.data.remote.dto.CarsPageDto> resp =
                api.getCars(
                        emptyToNull(status),
                        emptyToNull(make),
                        emptyToNull(near),
                        limit,
                        offset
                ).execute();
        if (resp.isSuccessful() && resp.body() != null) {
            String demoHint = resp.headers().get(HEADER_DEMO_HINT);
            return new CarsRemoteResult(resp.body(), demoHint);
        }
        throw toApiException(resp);
    }

    @NonNull
    public CarDto getCarById(long id) throws IOException, ApiException {
        Response<CarDto> resp = api.getCarById(id).execute();
        if (resp.isSuccessful() && resp.body() != null) return resp.body();
        throw toApiException(resp);
    }

    @NonNull
    private ApiException toApiException(@NonNull Response<?> resp) {
        ApiException parsed = errorParser.tryParse(resp.code(), resp.errorBody());
        if (parsed != null) return parsed;
        return new ApiException(resp.code(), "http_error", "HTTP " + resp.code(), null);
    }

    @Nullable
    private static String emptyToNull(@NonNull String s) {
        String trimmed = s.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
