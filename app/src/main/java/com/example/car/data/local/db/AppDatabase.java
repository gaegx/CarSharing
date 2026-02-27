package com.example.car.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.car.data.local.db.dao.BookingDao;
import com.example.car.data.local.db.entity.BookingEntity;

@Database(entities = {BookingEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookingDao bookingDao();
}
