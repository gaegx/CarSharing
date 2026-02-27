package com.example.car.di;

import android.app.Application;

import androidx.room.Room;

import com.example.car.data.local.db.AppDatabase;
import com.example.car.data.local.db.dao.BookingDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class DatabaseModule {

    @Provides
    @Singleton
    public static AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                AppDatabase.class,
                "car_app_db"
        ).build();
    }

    @Provides
    @Singleton
    public static BookingDao provideBookingDao(AppDatabase database) {
        return database.bookingDao();
    }
}
