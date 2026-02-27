package com.example.car.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.car.data.local.db.entity.BookingEntity;

import java.util.List;

@Dao
public interface BookingDao {

    @Query("SELECT * FROM bookings ORDER BY dateTimestamp DESC")
    List<BookingEntity> getAll();

    @Query("SELECT * FROM bookings WHERE id = :id")
    BookingEntity getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(BookingEntity entity);

    @Update
    void update(BookingEntity entity);

    @Query("DELETE FROM bookings WHERE id = :id")
    void deleteById(long id);
}
