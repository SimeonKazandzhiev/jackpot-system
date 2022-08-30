package com.example.jackpot.exception;

public class UnableReadRandomPropertiesException extends RuntimeException{
    public UnableReadRandomPropertiesException() {
    }

    public UnableReadRandomPropertiesException(String message) {
        super(message);
    }

    public UnableReadRandomPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableReadRandomPropertiesException(Throwable cause) {
        super(cause);
    }
}
