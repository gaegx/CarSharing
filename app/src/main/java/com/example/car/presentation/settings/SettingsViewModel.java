package com.example.car.presentation.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car.data.local.PreferencesManager;
import com.example.car.presentation.common.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class SettingsViewModel extends BaseViewModel {

    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_SYSTEM = 2;

    private final PreferencesManager preferencesManager;
    private final MutableLiveData<Integer> themeMode = new MutableLiveData<>();
    private final MutableLiveData<String> languageCode = new MutableLiveData<>();

    @Inject
    public SettingsViewModel(@NonNull PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
        themeMode.setValue(preferencesManager.getThemeMode());
        languageCode.setValue(preferencesManager.getLanguage());
    }

    public LiveData<Integer> getThemeMode() {
        return themeMode;
    }

    public LiveData<String> getLanguageCode() {
        return languageCode;
    }

    public void setThemeMode(int mode) {
        preferencesManager.setThemeMode(mode);
        themeMode.setValue(mode);
    }

    public void setLanguage(@NonNull String code) {
        preferencesManager.setLanguage(code);
        languageCode.setValue(code);
    }

    public int getCurrentThemeMode() {
        return preferencesManager.getThemeMode();
    }

    @NonNull
    public String getCurrentLanguage() {
        return preferencesManager.getLanguage();
    }
}
