package com.example.car.data.remote.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.car.data.dto.CarDto;

import java.util.List;

/**
 * Contract for car catalog API (mock or real).
 */
public interface CarApi {

    @NonNull
    List<CarDto> getCars();

    @Nullable
    CarDto getCarById(@NonNull String id);
}
