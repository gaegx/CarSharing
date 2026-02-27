package com.example.car.domain.repository;

import androidx.annotation.NonNull;

import com.example.car.domain.model.Booking;

import java.util.List;

/**
 * Repository contract for local bookings (Room CRUD).
 */
public interface BookingRepository {

    void getAll(@NonNull ResultCallback<List<Booking>> callback);

    void getById(long id, @NonNull ResultCallback<Booking> callback);

    void insert(@NonNull Booking booking, @NonNull ResultCallback<Long> callback);

    void update(@NonNull Booking booking, @NonNull ResultCallback<Void> callback);

    void delete(@NonNull Booking booking, @NonNull ResultCallback<Void> callback);
}
