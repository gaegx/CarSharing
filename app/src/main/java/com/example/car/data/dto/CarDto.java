package com.example.car.data.dto;

import androidx.annotation.NonNull;

/**
 * DTO for car from remote/mock API.
 */
public final class CarDto {

    private final String id;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final double pricePerMinute;

    public CarDto(@NonNull String id,
                  @NonNull String name,
                  @NonNull String description,
                  @NonNull String imageUrl,
                  double pricePerMinute) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.pricePerMinute = pricePerMinute;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }
}
