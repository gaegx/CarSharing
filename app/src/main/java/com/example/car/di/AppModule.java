package com.example.car.di;

import android.content.Context;

import com.example.car.data.local.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class AppModule {

    @Provides
    @Singleton
    public static PreferencesManager providePreferencesManager(@ApplicationContext Context context) {
        return new PreferencesManager(context);
    }
}
