package com.example.car.domain.usecase;

import androidx.annotation.NonNull;

import com.example.car.domain.model.Booking;
import com.example.car.domain.repository.BookingRepository;
import com.example.car.domain.repository.ResultCallback;
import com.example.car.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;

public final class GetBookingsUseCase {

    private final BookingRepository bookingRepository;
    private final AppExecutors executors;

    @Inject
    public GetBookingsUseCase(@NonNull BookingRepository bookingRepository,
                             @NonNull AppExecutors executors) {
        this.bookingRepository = bookingRepository;
        this.executors = executors;
    }

    public void execute(@NonNull ResultCallback<List<Booking>> callback) {
        executors.runOnDiskIo(() ->
                bookingRepository.getAll(result -> executors.runOnMainThread(() -> callback.onResult(result)))
        );
    }
}
