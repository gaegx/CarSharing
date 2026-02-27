package com.example.car.data.remote.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.car.data.dto.CarDto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mock implementation of CarApi with hardcoded data.
 */
@Singleton
public final class MockCarApi implements CarApi {

    private final List<CarDto> cars = new ArrayList<>();

    @Inject
    public MockCarApi() {
        cars.add(new CarDto("1", "Toyota Camry", "Comfortable sedan for city trips", "", 5.0));
        cars.add(new CarDto("2", "Hyundai Solaris", "Economy class, low fuel consumption", "", 3.5));
        cars.add(new CarDto("3", "Kia Rio", "Compact and agile", "", 4.0));
        cars.add(new CarDto("4", "Volkswagen Polo", "Reliable German quality", "", 4.5));
        cars.add(new CarDto("5", "Skoda Octavia", "Spacious trunk, family trips", "", 5.5));
    }

    @NonNull
    @Override
    public List<CarDto> getCars() {
        return new ArrayList<>(cars);
    }

    @Nullable
    @Override
    public CarDto getCarById(@NonNull String id) {
        for (CarDto car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }
}
