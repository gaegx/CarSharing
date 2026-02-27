package com.example.car.di;

import com.example.car.util.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class ExecutorModule {

    @Provides
    @Singleton
    public static AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }
}
