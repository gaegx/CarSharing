package com.example.car.presentation.booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car.domain.model.Booking;
import com.example.car.domain.usecase.CreateBookingUseCase;
import com.example.car.domain.usecase.GetBookingByIdUseCase;
import com.example.car.domain.usecase.UpdateBookingUseCase;
import com.example.car.presentation.common.BaseViewModel;
import com.example.car.presentation.state.Result;
import com.example.car.presentation.state.UiState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class CreateEditBookingViewModel extends BaseViewModel {

    private final CreateBookingUseCase createBookingUseCase;
    private final UpdateBookingUseCase updateBookingUseCase;
    private final GetBookingByIdUseCase getBookingByIdUseCase;
    private final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<String> saveError = new MutableLiveData<>();
    private final MutableLiveData<UiState<Booking>> bookingState = new MutableLiveData<>();

    @Inject
    public CreateEditBookingViewModel(@NonNull CreateBookingUseCase createBookingUseCase,
                                     @NonNull UpdateBookingUseCase updateBookingUseCase,
                                     @NonNull GetBookingByIdUseCase getBookingByIdUseCase) {
        this.createBookingUseCase = createBookingUseCase;
        this.updateBookingUseCase = updateBookingUseCase;
        this.getBookingByIdUseCase = getBookingByIdUseCase;
    }

    public LiveData<Boolean> getSaveSuccess() {
        return saveSuccess;
    }

    public LiveData<String> getSaveError() {
        return saveError;
    }

    public LiveData<UiState<Booking>> getBookingState() {
        return bookingState;
    }

    public void loadBooking(long bookingId) {
        getBookingByIdUseCase.execute(bookingId, result -> {
            if (result.isSuccess() && result.getData() != null) {
                bookingState.setValue(UiState.success(result.getData()));
            } else {
                bookingState.setValue(UiState.error(
                        result.getMessage() != null ? result.getMessage() : "Not found"));
            }
        });
    }

    public void saveBooking(long id, @NonNull String title, @NonNull String description, long dateTimestamp, @Nullable String carId) {
        if (title.trim().isEmpty()) {
            saveError.setValue("Title is required");
            return;
        }
        Booking booking = new Booking(id, title.trim(), description.trim(), dateTimestamp, carId);
        if (id == 0) {
            createBookingUseCase.execute(booking, result -> handleSaveResult(result, true));
        } else {
            updateBookingUseCase.execute(booking, result -> handleSaveResult(result, false));
        }
    }

    private void handleSaveResult(Result<?> result, boolean isCreate) {
        if (result.isSuccess()) {
            saveSuccess.setValue(true);
        } else {
            saveError.setValue(result.getMessage());
        }
    }

    public void clearSaveResult() {
        saveSuccess.setValue(false);
        saveError.setValue(null);
    }
}
