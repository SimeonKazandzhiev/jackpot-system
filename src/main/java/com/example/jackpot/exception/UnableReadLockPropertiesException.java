package com.example.jackpot.exception;

public class UnableReadLockPropertiesException extends RuntimeException{
    public UnableReadLockPropertiesException() {
    }

    public UnableReadLockPropertiesException(String message) {
        super(message);
    }

    public UnableReadLockPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableReadLockPropertiesException(Throwable cause) {
        super(cause);
    }
}
