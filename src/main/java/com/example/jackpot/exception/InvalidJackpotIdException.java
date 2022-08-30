package com.example.jackpot.exception;

public class InvalidJackpotIdException  extends RuntimeException{

    public InvalidJackpotIdException() {
    }

    public InvalidJackpotIdException(String message) {
        super(message);
    }

    public InvalidJackpotIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidJackpotIdException(Throwable cause) {
        super(cause);
    }


}
