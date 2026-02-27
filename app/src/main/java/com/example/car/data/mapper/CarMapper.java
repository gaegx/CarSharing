package com.example.car.data.mapper;

import androidx.annotation.NonNull;

import com.example.car.data.dto.CarDto;
import com.example.car.domain.model.Car;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class CarMapper {

    @Inject
    public CarMapper() {
    }

    @NonNull
    public Car toDomain(@NonNull CarDto dto) {
        return new Car(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getImageUrl(),
                dto.getPricePerMinute()
        );
    }
}
