package com.example.jackpot.exception;

public class CasinoCreationException extends RuntimeException{

    public CasinoCreationException() {
    }

    public CasinoCreationException(String message) {
        super(message);
    }

    public CasinoCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CasinoCreationException(Throwable cause) {
        super(cause);
    }

}
