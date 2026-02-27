package com.example.car.domain.repository;

import androidx.annotation.NonNull;

import com.example.car.presentation.state.Result;

/**
 * Callback for async repository operations.
 */
public interface ResultCallback<T> {
    void onResult(@NonNull Result<T> result);
}
