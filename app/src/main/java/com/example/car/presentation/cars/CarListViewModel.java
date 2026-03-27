package com.example.car.presentation.cars;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car.domain.model.Car;
import com.example.car.domain.model.CarListResult;
import com.example.car.domain.model.CarQuery;
import com.example.car.domain.usecase.GetCarsUseCase;
import com.example.car.domain.usecase.ImportCarsUseCase;
import com.example.car.data.network.NetworkMonitor;
import com.example.car.data.network.NetworkStatus;
import com.example.car.presentation.common.BaseViewModel;
import com.example.car.presentation.state.UiState;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class CarListViewModel extends BaseViewModel {

    private final GetCarsUseCase getCarsUseCase;
    private final ImportCarsUseCase importCarsUseCase;
    private final NetworkMonitor networkMonitor;
    private final MutableLiveData<UiState<List<Car>>> carListState = new MutableLiveData<>(UiState.loading());
    private final MutableLiveData<UiState<Integer>> importState = new MutableLiveData<>(UiState.success(0));
    private final MutableLiveData<String> bannerText = new MutableLiveData<>(null);

    @Inject
    public CarListViewModel(@NonNull GetCarsUseCase getCarsUseCase,
                            @NonNull ImportCarsUseCase importCarsUseCase,
                            @NonNull NetworkMonitor networkMonitor) {
        this.getCarsUseCase = getCarsUseCase;
        this.importCarsUseCase = importCarsUseCase;
        this.networkMonitor = networkMonitor;
        loadCars();
    }

    public LiveData<UiState<List<Car>>> getCarListState() {
        return carListState;
    }

    public LiveData<UiState<Integer>> getImportState() {
        return importState;
    }

    public LiveData<String> getBannerText() {
        return bannerText;
    }

    public LiveData<NetworkStatus> getNetworkStatus() {
        return networkMonitor.status();
    }

    public void loadCars() {
        carListState.setValue(UiState.loading());
        getCarsUseCase.execute(CarQuery.defaultList(), result -> {
            if (result.isSuccess() && result.getData() != null) {
                CarListResult data = result.getData();
                List<Car> list = data.getItems();
                if (data.getDemoHint() != null && !data.getDemoHint().trim().isEmpty()) {
                    bannerText.setValue(data.getDemoHint());
                } else if (data.isFromCache()) {
                    bannerText.setValue("Offline mode: showing cached data");
                } else {
                    bannerText.setValue(null);
                }
                carListState.setValue(list.isEmpty() ? UiState.empty() : UiState.success(list));
            } else {
                bannerText.setValue(null);
                carListState.setValue(UiState.error(
                        result.getMessage() != null ? result.getMessage() : "Unknown error"));
            }
        });
    }

    public void retry() {
        loadCars();
    }

    public void importCars(boolean force) {
        importState.setValue(UiState.loading());
        importCarsUseCase.execute(force, result -> {
            if (result.isSuccess() && result.getData() != null) {
                importState.setValue(UiState.success(result.getData()));
                loadCars();
            } else {
                importState.setValue(UiState.error(result.getMessage() != null ? result.getMessage() : "Import failed"));
            }
        });
    }
}
