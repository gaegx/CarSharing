package com.example.car.presentation.state;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * UI state for screens: Loading, Success, Error, Empty.
 */
public abstract class UiState<T> {

    @Nullable
    public abstract T getData();

    @Nullable
    public abstract String getMessage();

    public abstract Status getStatus();

    public boolean isLoading() {
        return getStatus() == Status.LOADING;
    }

    public boolean isSuccess() {
        return getStatus() == Status.SUCCESS;
    }

    public boolean isError() {
        return getStatus() == Status.ERROR;
    }

    public boolean isEmpty() {
        return getStatus() == Status.EMPTY;
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR,
        EMPTY
    }

    @NonNull
    public static <T> UiState<T> loading() {
        return new Loading<>();
    }

    @NonNull
    public static <T> UiState<T> success(@NonNull T data) {
        return new Success<>(data);
    }

    @NonNull
    public static <T> UiState<T> error(@NonNull String message) {
        return new Error<>(message);
    }

    @NonNull
    public static <T> UiState<T> empty() {
        return new Empty<>();
    }

    public static final class Loading<T> extends UiState<T> {
        @Override
        public T getData() {
            return null;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public Status getStatus() {
            return Status.LOADING;
        }
    }

    public static final class Success<T> extends UiState<T> {
        private final T data;

        Success(T data) {
            this.data = data;
        }

        @NonNull
        @Override
        public T getData() {
            return data;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public Status getStatus() {
            return Status.SUCCESS;
        }
    }

    public static final class Error<T> extends UiState<T> {
        private final String message;

        Error(String message) {
            this.message = message;
        }

        @Override
        public T getData() {
            return null;
        }

        @NonNull
        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public Status getStatus() {
            return Status.ERROR;
        }
    }

    public static final class Empty<T> extends UiState<T> {
        @Override
        public T getData() {
            return null;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public Status getStatus() {
            return Status.EMPTY;
        }
    }
}
