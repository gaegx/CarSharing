package com.example.car.data.local.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.car.data.local.db.dao.BookingDao;
import com.example.car.data.local.db.entity.BookingEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class BookingLocalDataSource {

    private final BookingDao dao;

    @Inject
    public BookingLocalDataSource(@NonNull BookingDao dao) {
        this.dao = dao;
    }

    @NonNull
    public List<BookingEntity> getAll() {
        return dao.getAll();
    }

    @Nullable
    public BookingEntity getById(long id) {
        return dao.getById(id);
    }

    public long insert(@NonNull BookingEntity entity) {
        return dao.insert(entity);
    }

    public void update(@NonNull BookingEntity entity) {
        dao.update(entity);
    }

    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
