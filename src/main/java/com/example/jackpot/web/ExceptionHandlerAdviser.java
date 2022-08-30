package com.example.jackpot.web;

import com.example.jackpot.exception.*;
import com.example.jackpot.model.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdviser {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleNonExistingCasinoException(NonExistingCasinoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDto(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleNonExistingEntityException(NonExistingEntityException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDto(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleBetAmountException(BetAmountException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleCasinoUpdatingException(CasinoUpdatingException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleInsufficientBalanceException(InsufficientBalanceException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleInvalidJackpotIdException(InvalidJackpotIdException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDto(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleInvalidJackpotLevelsCountException(InvalidJackpotLevelsCountException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleJackpotUpdatingException(JackpotUpdatingException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleExistingCasinoException(ExistingCasinoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
    }
}
