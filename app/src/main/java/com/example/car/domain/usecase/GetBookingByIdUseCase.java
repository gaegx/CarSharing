package com.example.car.domain.usecase;

import androidx.annotation.NonNull;

import com.example.car.domain.model.Booking;
import com.example.car.domain.repository.BookingRepository;
import com.example.car.domain.repository.ResultCallback;
import com.example.car.util.AppExecutors;

import javax.inject.Inject;

public final class GetBookingByIdUseCase {

    private final BookingRepository bookingRepository;
    private final AppExecutors executors;

    @Inject
    public GetBookingByIdUseCase(@NonNull BookingRepository bookingRepository,
                                @NonNull AppExecutors executors) {
        this.bookingRepository = bookingRepository;
        this.executors = executors;
    }

    public void execute(long id, @NonNull ResultCallback<Booking> callback) {
        executors.runOnDiskIo(() ->
                bookingRepository.getById(id, result -> executors.runOnMainThread(() -> callback.onResult(result)))
        );
    }
}
