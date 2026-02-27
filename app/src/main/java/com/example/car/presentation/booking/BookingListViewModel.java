package com.example.car.presentation.booking;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car.domain.model.Booking;
import com.example.car.domain.usecase.DeleteBookingUseCase;
import com.example.car.domain.usecase.GetBookingsUseCase;
import com.example.car.presentation.common.BaseViewModel;
import com.example.car.presentation.state.UiState;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class BookingListViewModel extends BaseViewModel {

    private final GetBookingsUseCase getBookingsUseCase;
    private final DeleteBookingUseCase deleteBookingUseCase;
    private final MutableLiveData<UiState<List<Booking>>> bookingsState = new MutableLiveData<>(UiState.loading());

    @Inject
    public BookingListViewModel(@NonNull GetBookingsUseCase getBookingsUseCase,
                                @NonNull DeleteBookingUseCase deleteBookingUseCase) {
        this.getBookingsUseCase = getBookingsUseCase;
        this.deleteBookingUseCase = deleteBookingUseCase;
    }

    public LiveData<UiState<List<Booking>>> getBookingsState() {
        return bookingsState;
    }

    public void loadBookings() {
        bookingsState.setValue(UiState.loading());
        getBookingsUseCase.execute(result -> {
            if (result.isSuccess() && result.getData() != null) {
                List<Booking> list = result.getData();
                bookingsState.setValue(list.isEmpty() ? UiState.empty() : UiState.success(list));
            } else {
                bookingsState.setValue(UiState.error(
                        result.getMessage() != null ? result.getMessage() : "Unknown error"));
            }
        });
    }

    public void deleteBooking(@NonNull Booking booking) {
        deleteBookingUseCase.execute(booking, result -> {
            if (result.isSuccess()) {
                loadBookings();
            }
        });
    }

    public void retry() {
        loadBookings();
    }
}
