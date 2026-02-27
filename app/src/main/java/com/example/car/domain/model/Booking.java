package com.example.car.domain.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * Domain model for a booking (local DB entity with title, description, date).
 */
public final class Booking {

    private final long id;
    @NonNull
    private final String title;
    @NonNull
    private final String description;
    private final long dateTimestamp;
    @Nullable
    private final String carId;

    public Booking(long id,
                   @NonNull String title,
                   @NonNull String description,
                   long dateTimestamp,
                   @Nullable String carId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTimestamp = dateTimestamp;
        this.carId = carId;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public long getDateTimestamp() {
        return dateTimestamp;
    }

    @Nullable
    public String getCarId() {
        return carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id
                && dateTimestamp == booking.dateTimestamp
                && title.equals(booking.title)
                && description.equals(booking.description)
                && Objects.equals(carId, booking.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dateTimestamp, carId);
    }
}
