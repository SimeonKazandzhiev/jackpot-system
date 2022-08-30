package com.example.jackpot.exception;

public class JackpotUpdatingException  extends RuntimeException{

    public JackpotUpdatingException() {
    }

    public JackpotUpdatingException(String message) {
        super(message);
    }

    public JackpotUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JackpotUpdatingException(Throwable cause) {
        super(cause);
    }

}
