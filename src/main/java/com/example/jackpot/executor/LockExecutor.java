package com.example.jackpot.executor;

import com.example.jackpot.config.LockConfig;
import com.example.jackpot.exception.ThreadInterruptedException;
import com.example.jackpot.exception.UnableToObtainLockException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

@Slf4j
public class LockExecutor {
    private final Integer TIME_RATE = LockConfig.getProperties();
    private final ReentrantLock lock = new ReentrantLock();

    public <T, R> R executeUnderLock(final T type, final Function<T, R> function){
        final boolean obtainedLock;

        try {
            obtainedLock = lock.tryLock(TIME_RATE, TimeUnit.MILLISECONDS);
        } catch (final InterruptedException ie) {
            log.error("Thread: " + Thread.currentThread().getName() + " was interrupted at: " + LocalDateTime.now());
            throw new ThreadInterruptedException("Current thread: " + Thread.currentThread().getName() + " was interrupted");
        }

        if (obtainedLock) {
            try {
                return function.apply(type);
            } finally {
                lock.unlock();
            }
        } else {
            log.error("Thread: " + Thread.currentThread() + " was unable to obtain lock.");
            throw new UnableToObtainLockException("Waiting time for lock expired.");
        }
    }
}
