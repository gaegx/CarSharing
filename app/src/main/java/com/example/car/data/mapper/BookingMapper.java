package com.example.car.data.mapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.car.data.local.db.entity.BookingEntity;
import com.example.car.domain.model.Booking;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class BookingMapper {

    @Inject
    public BookingMapper() {
    }

    @NonNull
    public Booking toDomain(@NonNull BookingEntity entity) {
        return new Booking(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDateTimestamp(),
                entity.getCarId()
        );
    }

    @NonNull
    public BookingEntity toEntity(@NonNull Booking booking) {
        return new BookingEntity(
                booking.getId(),
                booking.getTitle(),
                booking.getDescription(),
                booking.getDateTimestamp(),
                booking.getCarId()
        );
    }
}
