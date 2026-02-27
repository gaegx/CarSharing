package com.example.car.domain.repository;

import androidx.annotation.NonNull;

import com.example.car.domain.model.Car;

import java.util.List;

/**
 * Repository contract for car catalog (remote/mock).
 */
public interface CarRepository {

    void getCars(@NonNull ResultCallback<List<Car>> callback);

    void getCarById(@NonNull String id, @NonNull ResultCallback<Car> callback);
}
