package com.example.car.domain.usecase;

import androidx.annotation.NonNull;

import com.example.car.domain.model.Car;
import com.example.car.domain.repository.CarRepository;
import com.example.car.domain.repository.ResultCallback;
import com.example.car.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;

public final class GetCarsUseCase {

    private final CarRepository carRepository;
    private final AppExecutors executors;

    @Inject
    public GetCarsUseCase(@NonNull CarRepository carRepository,
                         @NonNull AppExecutors executors) {
        this.carRepository = carRepository;
        this.executors = executors;
    }

    public void execute(@NonNull ResultCallback<List<Car>> callback) {
        executors.runOnDiskIo(() ->
                carRepository.getCars(result -> executors.runOnMainThread(() -> callback.onResult(result)))
        );
    }
}
