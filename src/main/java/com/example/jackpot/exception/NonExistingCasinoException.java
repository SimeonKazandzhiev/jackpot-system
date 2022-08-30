package com.example.jackpot.exception;

public class NonExistingCasinoException  extends RuntimeException{

    public NonExistingCasinoException() {
    }

    public NonExistingCasinoException(String message) {
        super(message);
    }

    public NonExistingCasinoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExistingCasinoException(Throwable cause) {
        super(cause);
    }

}
