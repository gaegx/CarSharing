package com.example.car.presentation.splash;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.car.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class SplashFragment extends Fragment {

    private static final long SPLASH_DELAY_MS = 2000L;

    private SplashViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        viewModel.getNavigateToMain().observe(getViewLifecycleOwner(), shouldNavigate -> {
            if (Boolean.TRUE.equals(shouldNavigate)) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_splash_to_carList);
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(
                () -> viewModel.onSplashFinished(),
                SPLASH_DELAY_MS
        );
    }
}
