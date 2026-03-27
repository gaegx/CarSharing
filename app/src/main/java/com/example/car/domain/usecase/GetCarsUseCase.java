package com.example.car.domain.usecase;

import androidx.annotation.NonNull;

import com.example.car.domain.model.CarListResult;
import com.example.car.domain.model.CarQuery;
import com.example.car.domain.repository.CarRepository;
import com.example.car.domain.repository.ResultCallback;
import com.example.car.util.AppExecutors;

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

    public void execute(@NonNull CarQuery query, @NonNull ResultCallback<CarListResult> callback) {
        executors.runOnDiskIo(() ->
                carRepository.getCars(query, result -> executors.runOnMainThread(() -> callback.onResult(result)))
        );
    }
}
