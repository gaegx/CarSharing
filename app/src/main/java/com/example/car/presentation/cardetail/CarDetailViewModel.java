package com.example.car.presentation.cardetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car.domain.model.Car;
import com.example.car.domain.usecase.GetCarByIdUseCase;
import com.example.car.presentation.common.BaseViewModel;
import com.example.car.presentation.state.UiState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class CarDetailViewModel extends BaseViewModel {

    private final GetCarByIdUseCase getCarByIdUseCase;
    private final MutableLiveData<UiState<Car>> carState = new MutableLiveData<>(UiState.loading());

    @Inject
    public CarDetailViewModel(@NonNull GetCarByIdUseCase getCarByIdUseCase) {
        this.getCarByIdUseCase = getCarByIdUseCase;
    }

    public LiveData<UiState<Car>> getCarState() {
        return carState;
    }

    public void loadCar(@NonNull String carId) {
        carState.setValue(UiState.loading());
        getCarByIdUseCase.execute(carId, result -> {
            if (result.isSuccess() && result.getData() != null) {
                carState.setValue(UiState.success(result.getData()));
            } else {
                carState.setValue(UiState.error(
                        result.getMessage() != null ? result.getMessage() : "Unknown error"));
            }
        });
    }

    public void retry(@NonNull String carId) {
        loadCar(carId);
    }
}
