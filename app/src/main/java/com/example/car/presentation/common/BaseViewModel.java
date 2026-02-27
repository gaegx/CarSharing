package com.example.car.presentation.common;

import androidx.annotation.CallSuper;
import androidx.lifecycle.ViewModel;

/**
 * Base ViewModel for common setup. No references to View/Fragment.
 */
public abstract class BaseViewModel extends ViewModel {

    @CallSuper
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
