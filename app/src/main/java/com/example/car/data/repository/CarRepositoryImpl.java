package com.example.car.data.repository;

import androidx.annotation.NonNull;

import com.example.car.data.mapper.CarMapper;
import com.example.car.data.remote.datasource.CarRemoteDataSource;
import com.example.car.domain.model.Car;
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
    private final CarMapper mapper;
    private final AppExecutors executors;

    @Inject
    public CarRepositoryImpl(@NonNull CarRemoteDataSource remoteDataSource,
                             @NonNull CarMapper mapper,
                             @NonNull AppExecutors executors) {
        this.remoteDataSource = remoteDataSource;
        this.mapper = mapper;
        this.executors = executors;
    }

    @Override
    public void getCars(@NonNull ResultCallback<List<Car>> callback) {
        executors.runOnDiskIo(() -> {
            try {
                List<Car> cars = new ArrayList<>();
                for (var dto : remoteDataSource.getCars()) {
                    cars.add(mapper.toDomain(dto));
                }
                Result<List<Car>> result = Result.success(cars);
                executors.runOnMainThread(() -> callback.onResult(result));
            } catch (Exception e) {
                executors.runOnMainThread(() -> callback.onResult(Result.error(e.getMessage(), e)));
            }
        });
    }

    @Override
    public void getCarById(@NonNull String id, @NonNull ResultCallback<Car> callback) {
        executors.runOnDiskIo(() -> {
            var dto = remoteDataSource.getCarById(id);
            if (dto == null) {
                executors.runOnMainThread(() -> callback.onResult(Result.error("Car not found: " + id)));
                return;
            }
            Car car = mapper.toDomain(dto);
            executors.runOnMainThread(() -> callback.onResult(Result.success(car)));
        });
    }
}
