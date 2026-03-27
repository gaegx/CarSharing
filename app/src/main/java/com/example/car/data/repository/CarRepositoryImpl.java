package com.example.car.data.repository;

import androidx.annotation.NonNull;

import com.example.car.data.mapper.CarMapper;
import com.example.car.data.local.datasource.CarLocalDataSource;
import com.example.car.data.local.db.entity.CarEntity;
import com.example.car.data.network.NetworkMonitor;
import com.example.car.data.remote.datasource.CarRemoteDataSource;
import com.example.car.data.remote.error.ApiException;
import com.example.car.domain.model.Car;
import com.example.car.domain.model.CarListResult;
import com.example.car.domain.model.CarQuery;
import com.example.car.domain.repository.CarRepository;
import com.example.car.domain.repository.ResultCallback;
import com.example.car.presentation.state.Result;
import com.example.car.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class CarRepositoryImpl implements CarRepository {

    private final CarRemoteDataSource remoteDataSource;
    private final CarLocalDataSource localDataSource;
    private final NetworkMonitor networkMonitor;
    private final CarMapper mapper;
    private final AppExecutors executors;

    @Inject
    public CarRepositoryImpl(@NonNull CarRemoteDataSource remoteDataSource,
                             @NonNull CarLocalDataSource localDataSource,
                             @NonNull NetworkMonitor networkMonitor,
                             @NonNull CarMapper mapper,
                             @NonNull AppExecutors executors) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.networkMonitor = networkMonitor;
        this.mapper = mapper;
        this.executors = executors;
    }

    @Override
    public void getCars(@NonNull CarQuery query, @NonNull ResultCallback<CarListResult> callback) {
        executors.runOnDiskIo(() -> {
            try {
                if (!networkMonitor.isOnline()) {
                    List<Car> cached = new ArrayList<>();
                    for (CarEntity e : localDataSource.getAll()) {
                        cached.add(mapper.toDomain(e));
                    }
                    Result<CarListResult> result = Result.success(new CarListResult(cached, true, null));
                    executors.runOnMainThread(() -> callback.onResult(result));
                    return;
                }

                String status = query.getStatus() != null ? query.getStatus() : "";
                String make = query.getMake() != null ? query.getMake() : "";
                String near = query.getNear() != null ? query.getNear().toQueryValue() : "";

                var remote = remoteDataSource.getCars(status, make, near, query.getLimit(), query.getOffset());
                List<Car> cars = new ArrayList<>();
                List<CarEntity> entities = new ArrayList<>();
                if (remote.getPage().getItems() != null) {
                    for (var dto : remote.getPage().getItems()) {
                        cars.add(mapper.toDomain(dto));
                        entities.add(mapper.toEntity(dto));
                    }
                }
                localDataSource.replaceAll(entities);

                Result<CarListResult> result = Result.success(new CarListResult(cars, false, remote.getDemoHint()));
                executors.runOnMainThread(() -> callback.onResult(result));
            } catch (Exception e) {
                executors.runOnMainThread(() -> callback.onResult(Result.error(toUserMessage(e), e)));
            }
        });
    }

    @Override
    public void getCarById(@NonNull String id, @NonNull ResultCallback<Car> callback) {
        executors.runOnDiskIo(() -> {
            try {
                if (!networkMonitor.isOnline()) {
                    CarEntity cached = localDataSource.getById(id);
                    if (cached == null) {
                        executors.runOnMainThread(() -> callback.onResult(Result.error("Offline: no cached data for car " + id)));
                        return;
                    }
                    executors.runOnMainThread(() -> callback.onResult(Result.success(mapper.toDomain(cached))));
                    return;
                }

                long numericId;
                try {
                    numericId = Long.parseLong(id);
                } catch (NumberFormatException nfe) {
                    executors.runOnMainThread(() -> callback.onResult(Result.error("Invalid car id: " + id, nfe)));
                    return;
                }

                var dto = remoteDataSource.getCarById(numericId);
                Car car = mapper.toDomain(dto);
                executors.runOnMainThread(() -> callback.onResult(Result.success(car)));
            } catch (Exception e) {
                executors.runOnMainThread(() -> callback.onResult(Result.error(toUserMessage(e), e)));
            }
        });
    }

    @Override
    public void importCars(boolean force, @NonNull ResultCallback<Integer> callback) {
        executors.runOnDiskIo(() -> {
            try {
                if (!networkMonitor.isOnline()) {
                    executors.runOnMainThread(() -> callback.onResult(Result.error("No internet connection")));
                    return;
                }
                var result = remoteDataSource.importCars(force);
                executors.runOnMainThread(() -> callback.onResult(Result.success(result.getImported())));
            } catch (Exception e) {
                executors.runOnMainThread(() -> callback.onResult(Result.error(toUserMessage(e), e)));
            }
        });
    }

    @NonNull
    private static String toUserMessage(@NonNull Exception e) {
        if (e instanceof ApiException) {
            String msg = ((ApiException) e).getApiMessage();
            return msg != null && !msg.trim().isEmpty() ? msg : "API error";
        }
        String msg = e.getMessage();
        return msg != null && !msg.trim().isEmpty() ? msg : "Unknown error";
    }
}
