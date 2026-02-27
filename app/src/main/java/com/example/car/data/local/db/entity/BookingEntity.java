package com.example.car.data.local.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings")
public final class BookingEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private final String title;
    private final String description;
    private final long dateTimestamp;
    private final String carId;

    public BookingEntity(long id,
                         String title,
                         String description,
                         long dateTimestamp,
                         String carId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTimestamp = dateTimestamp;
        this.carId = carId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getDateTimestamp() {
        return dateTimestamp;
    }

    public String getCarId() {
        return carId;
    }
}
