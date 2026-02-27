package com.example.car.presentation.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car.presentation.common.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class SplashViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> navigateToMain = new MutableLiveData<>();

    @Inject
    public SplashViewModel() {
        navigateToMain.setValue(false);
    }

    public LiveData<Boolean> getNavigateToMain() {
        return navigateToMain;
    }

    public void onSplashFinished() {
        navigateToMain.setValue(true);
    }
}
