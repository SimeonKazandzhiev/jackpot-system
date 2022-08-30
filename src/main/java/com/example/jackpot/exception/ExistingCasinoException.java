package com.example.jackpot.exception;

public class ExistingCasinoException extends RuntimeException{

    public ExistingCasinoException() {
    }

    public ExistingCasinoException(String message) {
        super(message);
    }

    public ExistingCasinoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingCasinoException(Throwable cause) {
        super(cause);
    }

}
