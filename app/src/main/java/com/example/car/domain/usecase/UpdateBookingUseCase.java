package com.example.car.domain.usecase;

import androidx.annotation.NonNull;

import com.example.car.domain.model.Booking;
import com.example.car.domain.repository.BookingRepository;
import com.example.car.domain.repository.ResultCallback;
import com.example.car.util.AppExecutors;

import javax.inject.Inject;

public final class UpdateBookingUseCase {

    private final BookingRepository bookingRepository;
    private final AppExecutors executors;

    @Inject
    public UpdateBookingUseCase(@NonNull BookingRepository bookingRepository,
                               @NonNull AppExecutors executors) {
        this.bookingRepository = bookingRepository;
        this.executors = executors;
    }

    public void execute(@NonNull Booking booking, @NonNull ResultCallback<Void> callback) {
        executors.runOnDiskIo(() ->
                bookingRepository.update(booking, result -> executors.runOnMainThread(() -> callback.onResult(result)))
        );
    }
}
