package com.example.jackpot.exception;

public class InvalidJackpotLevelsCountException extends RuntimeException{

    public InvalidJackpotLevelsCountException() {
    }

    public InvalidJackpotLevelsCountException(String message) {
        super(message);
    }

    public InvalidJackpotLevelsCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidJackpotLevelsCountException(Throwable cause) {
        super(cause);
    }
}
