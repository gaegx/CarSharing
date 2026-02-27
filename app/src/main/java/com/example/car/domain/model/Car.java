package com.example.car.domain.model;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Domain model for a car in the car-sharing catalog.
 */
public final class Car {

    private final String id;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final double pricePerMinute;

    public Car(@NonNull String id,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(car.pricePerMinute, pricePerMinute) == 0
                && id.equals(car.id)
                && name.equals(car.name)
                && description.equals(car.description)
                && imageUrl.equals(car.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, pricePerMinute);
    }
}
