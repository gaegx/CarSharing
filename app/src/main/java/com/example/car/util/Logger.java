package com.example.car.util;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Abstraction over Android Log for testability and optional production filtering.
 */
@Singleton
public final class Logger {

    private static final String DEFAULT_TAG = "CarApp";

    @Inject
    public Logger() {
    }

    public void d(String tag, String message) {
        Log.d(tag != null ? tag : DEFAULT_TAG, message);
    }

    public void d(String message) {
        d(DEFAULT_TAG, message);
    }

    public void i(String tag, String message) {
        Log.i(tag != null ? tag : DEFAULT_TAG, message);
    }

    public void w(String tag, String message) {
        Log.w(tag != null ? tag : DEFAULT_TAG, message);
    }

    public void e(String tag, String message) {
        Log.e(tag != null ? tag : DEFAULT_TAG, message);
    }

    public void e(String tag, String message, Throwable throwable) {
        Log.e(tag != null ? tag : DEFAULT_TAG, message, throwable);
    }
}
