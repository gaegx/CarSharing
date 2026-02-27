package com.example.car.presentation.settings;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.car.R;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class SettingsFragment extends Fragment {

    private SettingsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        RadioGroup themeGroup = view.findViewById(R.id.settings_theme_group);
        int currentTheme = viewModel.getCurrentThemeMode();
        int themeRadioId = R.id.settings_theme_light;
        if (currentTheme == SettingsViewModel.THEME_DARK) themeRadioId = R.id.settings_theme_dark;
        else if (currentTheme == SettingsViewModel.THEME_SYSTEM) themeRadioId = R.id.settings_theme_system;
        themeGroup.check(themeRadioId);

        themeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int mode = SettingsViewModel.THEME_LIGHT;
            if (checkedId == R.id.settings_theme_dark) mode = SettingsViewModel.THEME_DARK;
            else if (checkedId == R.id.settings_theme_system) mode = SettingsViewModel.THEME_SYSTEM;
            viewModel.setThemeMode(mode);
            applyTheme(mode);
        });

        RadioGroup langGroup = view.findViewById(R.id.settings_language_group);
        String currentLang = viewModel.getCurrentLanguage();
        int langRadioId = "ru".equals(currentLang) ? R.id.settings_lang_ru : R.id.settings_lang_en;
        langGroup.check(langRadioId);

        langGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String code = checkedId == R.id.settings_lang_ru ? "ru" : "en";
            viewModel.setLanguage(code);
            Toast.makeText(requireContext(), getString(R.string.settings_restart_hint), Toast.LENGTH_LONG).show();
            applyLanguage(code);
        });
    }

    private void applyTheme(int mode) {
        int nightMode = AppCompatDelegate.MODE_NIGHT_NO;
        if (mode == SettingsViewModel.THEME_DARK) nightMode = AppCompatDelegate.MODE_NIGHT_YES;
        else if (mode == SettingsViewModel.THEME_SYSTEM) nightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    private void applyLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        if (getActivity() != null) {
            getActivity().recreate();
        }
    }
}
