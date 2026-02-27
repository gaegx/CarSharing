package com.example.car.data.repository;

import androidx.annotation.NonNull;

import com.example.car.data.local.datasource.BookingLocalDataSource;
import com.example.car.data.local.db.entity.BookingEntity;
import com.example.car.data.mapper.BookingMapper;
import com.example.car.domain.model.Booking;
import com.example.car.domain.repository.BookingRepository;
import com.example.car.domain.repository.ResultCallback;
import com.example.car.presentation.state.Result;
import com.example.car.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class BookingRepositoryImpl implements BookingRepository {

    private final BookingLocalDataSource localDataSource;
    private final BookingMapper mapper;
    private final AppExecutors executors;

    @Inject
    public BookingRepositoryImpl(@NonNull BookingLocalDataSource localDataSource,
                                @NonNull BookingMapper mapper,
                                @NonNull AppExecutors executors) {
        this.localDataSource = localDataSource;
        this.mapper = mapper;
        this.executors = executors;
    }

    @Override
    public void getAll(@NonNull ResultCallback<List<Booking>> callback) {
        executors.runOnDiskIo(() -> {
            try {
                List<Booking> list = new ArrayList<>();
                for (BookingEntity entity : localDataSource.getAll()) {
                    list.add(mapper.toDomain(entity));
                }
                executors.runOnMainThread(() -> callback.onResult(Result.success(list)));
            } catch (Exception e) {
                executors.runOnMainThread(() -> callback.onResult(Result.error(e.getMessage(), e)));
            }
        });
    }

    @Override
    public void getById(long id, @NonNull ResultCallback<Booking> callback) {
        executors.runOnDiskIo(() -> {
            BookingEntity entity = localDataSource.getById(id);
            if (entity == null) {
                executors.runOnMainThread(() -> callback.onResult(Result.error("Booking not found")));
                return;
            }
            executors.runOnMainThread(() -> callback.onResult(Result.success(mapper.toDomain(entity))));
        });
    }

    @Override
    public void insert(@NonNull Booking booking, @NonNull ResultCallback<Long> callback) {
        executors.runOnDiskIo(() -> {
            try {
                BookingEntity entity = mapper.toEntity(booking);
                entity.setId(0);
                long id = localDataSource.insert(entity);
                executors.runOnMainThread(() -> callback.onResult(Result.success(id)));
            } catch (Exception e) {
                executors.runOnMainThread(() -> callback.onResult(Result.error(e.getMessage(), e)));
            }
        });
    }

    @Override
    public void update(@NonNull Booking booking, @NonNull ResultCallback<Void> callback) {
        executors.runOnDiskIo(() -> {
            try {
                localDataSource.update(mapper.toEntity(booking));
                executors.runOnMainThread(() -> callback.onResult(Result.success(null)));
            } catch (Exception e) {
                executors.runOnMainThread(() -> callback.onResult(Result.error(e.getMessage(), e)));
            }
        });
    }

    @Override
    public void delete(@NonNull Booking booking, @NonNull ResultCallback<Void> callback) {
        executors.runOnDiskIo(() -> {
            try {
                localDataSource.deleteById(booking.getId());
                executors.runOnMainThread(() -> callback.onResult(Result.success(null)));
            } catch (Exception e) {
                executors.runOnMainThread(() -> callback.onResult(Result.error(e.getMessage(), e)));
            }
        });
    }
}
