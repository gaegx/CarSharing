package com.example.car.presentation.cars;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car.domain.model.Car;
import com.example.car.domain.usecase.GetCarsUseCase;
import com.example.car.presentation.common.BaseViewModel;
import com.example.car.presentation.state.UiState;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class CarListViewModel extends BaseViewModel {

    private final GetCarsUseCase getCarsUseCase;
    private final MutableLiveData<UiState<List<Car>>> carListState = new MutableLiveData<>(UiState.loading());

    @Inject
    public CarListViewModel(@NonNull GetCarsUseCase getCarsUseCase) {
        this.getCarsUseCase = getCarsUseCase;
        loadCars();
    }

    public LiveData<UiState<List<Car>>> getCarListState() {
        return carListState;
    }

    public void loadCars() {
        carListState.setValue(UiState.loading());
        getCarsUseCase.execute(result -> {
            if (result.isSuccess() && result.getData() != null) {
                List<Car> list = result.getData();
                carListState.setValue(list.isEmpty() ? UiState.empty() : UiState.success(list));
            } else {
                carListState.setValue(UiState.error(
                        result.getMessage() != null ? result.getMessage() : "Unknown error"));
            }
        });
    }

    public void retry() {
        loadCars();
    }
}
