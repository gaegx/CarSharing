package com.example.car.data.remote.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.car.data.dto.CarDto;
import com.example.car.data.remote.api.CarApi;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class CarRemoteDataSource {

    private final CarApi api;

    @Inject
    public CarRemoteDataSource(@NonNull CarApi api) {
        this.api = api;
    }

    @NonNull
    public List<CarDto> getCars() {
        return api.getCars();
    }

    @Nullable
    public CarDto getCarById(@NonNull String id) {
        return api.getCarById(id);
    }
}
