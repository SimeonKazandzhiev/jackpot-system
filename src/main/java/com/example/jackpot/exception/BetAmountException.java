package com.example.jackpot.exception;

public class BetAmountException extends RuntimeException {

    public BetAmountException() {
    }

    public BetAmountException(String message) {
        super(message);
    }

    public BetAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public BetAmountException(Throwable cause) {
        super(cause);
    }

}
