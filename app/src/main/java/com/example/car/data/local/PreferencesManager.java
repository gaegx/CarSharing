package com.example.car.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Persists theme and language settings. Used at app start to apply saved theme.
 */
@Singleton
public final class PreferencesManager {

    private static final String PREFS_NAME = "car_app_prefs";
    private static final String KEY_THEME_MODE = "theme_mode"; // 0=light, 1=dark, 2=system
    private static final String KEY_LANGUAGE = "language";   // "en", "ru"

    private final SharedPreferences prefs;

    @Inject
    public PreferencesManager(@NonNull Context context) {
        this.prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public int getThemeMode() {
        return prefs.getInt(KEY_THEME_MODE, 2); // default system
    }

    public void setThemeMode(int mode) {
        prefs.edit().putInt(KEY_THEME_MODE, mode).apply();
    }

    @NonNull
    public String getLanguage() {
        return prefs.getString(KEY_LANGUAGE, "en");
    }

    public void setLanguage(@NonNull String languageCode) {
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply();
    }
}
