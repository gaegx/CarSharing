package com.example.car.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.car.data.local.db.dao.CarDao;
import com.example.car.data.local.db.dao.BookingDao;
import com.example.car.data.local.db.entity.BookingEntity;
import com.example.car.data.local.db.entity.CarEntity;

@Database(entities = {BookingEntity.class, CarEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookingDao bookingDao();

    public abstract CarDao carDao();
}
