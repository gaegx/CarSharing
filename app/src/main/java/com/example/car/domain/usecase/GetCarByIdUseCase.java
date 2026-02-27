package com.example.car.domain.usecase;

import androidx.annotation.NonNull;

import com.example.car.domain.model.Car;
import com.example.car.domain.repository.CarRepository;
import com.example.car.domain.repository.ResultCallback;
import com.example.car.util.AppExecutors;

import javax.inject.Inject;

public final class GetCarByIdUseCase {

    private final CarRepository carRepository;
    private final AppExecutors executors;

    @Inject
    public GetCarByIdUseCase(@NonNull CarRepository carRepository,
                            @NonNull AppExecutors executors) {
        this.carRepository = carRepository;
        this.executors = executors;
    }

    public void execute(@NonNull String carId, @NonNull ResultCallback<Car> callback) {
        executors.runOnDiskIo(() ->
                carRepository.getCarById(carId, result -> executors.runOnMainThread(() -> callback.onResult(result)))
        );
    }
}
