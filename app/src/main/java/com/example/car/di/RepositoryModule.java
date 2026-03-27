package com.example.car.di;

import com.example.car.domain.repository.BookingRepository;
import com.example.car.domain.repository.CarRepository;
import com.example.car.data.repository.BookingRepositoryImpl;
import com.example.car.data.repository.CarRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract CarRepository bindCarRepository(CarRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract BookingRepository bindBookingRepository(BookingRepositoryImpl impl);
}
