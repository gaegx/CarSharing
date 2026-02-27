package com.example.car.presentation.state;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Wrapper for async operation result: Success with data or Error with message/throwable.
 */
public abstract class Result<T> {

    private Result() {
    }

    @Nullable
    public abstract T getData();

    @Nullable
    public abstract String getMessage();

    @Nullable
    public abstract Throwable getError();

    public boolean isSuccess() {
        return this instanceof Success;
    }

    public boolean isError() {
        return this instanceof Error;
    }

    public static <T> Result<T> success(@NonNull T data) {
        return new Success<>(data);
    }

    public static <T> Result<T> error(@NonNull String message) {
        return new Error<>(message, null);
    }

    public static <T> Result<T> error(@NonNull String message, @Nullable Throwable throwable) {
        return new Error<>(message, throwable);
    }

    public static final class Success<T> extends Result<T> {
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
        public Throwable getError() {
            return null;
        }
    }

    public static final class Error<T> extends Result<T> {
        private final String message;
        private final Throwable throwable;

        Error(String message, Throwable throwable) {
            this.message = message;
            this.throwable = throwable;
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
        public Throwable getError() {
            return throwable;
        }
    }
}
