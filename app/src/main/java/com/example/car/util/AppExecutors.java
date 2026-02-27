package com.example.car.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provides disk IO, network and main thread executors for off-main work and LiveData postValue.
 */
@Singleton
public final class AppExecutors {

    private final Executor diskIo;
    private final Executor mainThread;

    @Inject
    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), new MainThreadExecutor());
    }

    public AppExecutors(@NonNull Executor diskIo, @NonNull Executor mainThread) {
        this.diskIo = diskIo;
        this.mainThread = mainThread;
    }

    public Executor getDiskIo() {
        return diskIo;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public void runOnDiskIo(@NonNull Runnable runnable) {
        diskIo.execute(runnable);
    }

    public void runOnMainThread(@NonNull Runnable runnable) {
        mainThread.execute(runnable);
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }
}
