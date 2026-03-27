package com.example.car.di;

import android.app.Application;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.car.data.local.db.AppDatabase;
import com.example.car.data.local.db.dao.BookingDao;
import com.example.car.data.local.db.dao.CarDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class DatabaseModule {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `cars` (" +
                            "`id` TEXT NOT NULL, " +
                            "`name` TEXT NOT NULL, " +
                            "`description` TEXT NOT NULL, " +
                            "`imageUrl` TEXT NOT NULL, " +
                            "`pricePerMinute` REAL NOT NULL, " +
                            "`updatedAt` TEXT NOT NULL, " +
                            "PRIMARY KEY(`id`)" +
                            ")"
            );
        }
    };

    @Provides
    @Singleton
    public static AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                AppDatabase.class,
                "car_app_db"
        ).addMigrations(MIGRATION_1_2).build();
    }

    @Provides
    @Singleton
    public static BookingDao provideBookingDao(AppDatabase database) {
        return database.bookingDao();
    }

    @Provides
    @Singleton
    public static CarDao provideCarDao(AppDatabase database) {
        return database.carDao();
    }
}
