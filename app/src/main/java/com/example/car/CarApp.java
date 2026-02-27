package com.example.car;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class CarApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        applySavedTheme();
    }

    private void applySavedTheme() {
        int mode = getSharedPreferences("car_app_prefs", MODE_PRIVATE).getInt("theme_mode", 2);
        int nightMode = AppCompatDelegate.MODE_NIGHT_NO;
        if (mode == 1) nightMode = AppCompatDelegate.MODE_NIGHT_YES;
        else if (mode == 2) nightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }
}
