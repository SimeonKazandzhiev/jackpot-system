package com.example.jackpot.exception;

public class CasinoUpdatingException extends RuntimeException{

    public CasinoUpdatingException() {
    }

    public CasinoUpdatingException(String message) {
        super(message);
    }

    public CasinoUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CasinoUpdatingException(Throwable cause) {
        super(cause);
    }
}
