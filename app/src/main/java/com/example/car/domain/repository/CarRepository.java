package com.example.car.domain.repository;

import androidx.annotation.NonNull;

import com.example.car.domain.model.Car;
import com.example.car.domain.model.CarListResult;
import com.example.car.domain.model.CarQuery;

import java.util.List;

/**
 * Repository contract for car catalog (remote/mock).
 */
public interface CarRepository {

    void getCars(@NonNull CarQuery query, @NonNull ResultCallback<CarListResult> callback);

    void getCarById(@NonNull String id, @NonNull ResultCallback<Car> callback);

    void importCars(boolean force, @NonNull ResultCallback<Integer> callback);
}
