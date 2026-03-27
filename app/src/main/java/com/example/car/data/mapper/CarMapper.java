package com.example.car.data.mapper;

import androidx.annotation.NonNull;

import com.example.car.data.local.db.entity.CarEntity;
import com.example.car.data.remote.dto.CarDto;
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
        String make = dto.getMake() != null ? dto.getMake() : "Unknown";
        String model = dto.getModel() != null ? dto.getModel() : "";
        String status = dto.getStatus() != null ? dto.getStatus() : "unknown";

        String name = (make + " " + model).trim();
        String description = "Year: " + dto.getYear()
                + " • Status: " + status
                + " • Seats: " + dto.getSeats();

        return new Car(
                String.valueOf(dto.getId()),
                name.isEmpty() ? "Car #" + dto.getId() : name,
                description,
                "",
                0.0
        );
    }

    @NonNull
    public Car toDomain(@NonNull CarEntity entity) {
        return new Car(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getPricePerMinute()
        );
    }

    @NonNull
    public CarEntity toEntity(@NonNull CarDto dto) {
        Car domain = toDomain(dto);
        String updatedAt = dto.getUpdatedAt() != null ? dto.getUpdatedAt() : "";
        return new CarEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getImageUrl(),
                domain.getPricePerMinute(),
                updatedAt
        );
    }
}
