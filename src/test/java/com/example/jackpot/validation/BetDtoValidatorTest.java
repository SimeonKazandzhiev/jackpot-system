package com.example.jackpot.validation;

import com.example.jackpot.exception.BetAmountException;
import com.example.jackpot.exception.NonExistingCasinoException;
import com.example.jackpot.exception.NonExistingEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BetDtoValidatorTest {


    private BetDtoValidator betDtoValidator;

    @BeforeEach
    void setup(){
        betDtoValidator = new BetDtoValidator();
    }

    @Test
    @DisplayName("validateJsonInput() method should pass.")
    void validateJsonInput() {
       betDtoValidator.validateBetJsonInput(BigDecimal.valueOf(10),2L,3L);
    }
    @Test
    @DisplayName("validateJsonInput() method should throw an exception.")
    void validateJsonInputInvalidAmount() {
        assertThrows(BetAmountException.class, () -> betDtoValidator
                .validateBetJsonInput(BigDecimal.valueOf(-10),3L,3L));
    }
    @Test
    @DisplayName("validateJsonInput() method should throw an exception.")
    void validateJsonInputInvalidCasinoId() {
        assertThrows(NonExistingCasinoException.class, () -> betDtoValidator
                .validateBetJsonInput(BigDecimal.valueOf(10),3L,-2L));
    }
    @Test
    @DisplayName("validateJsonInput() method should throw an exception.")
    void validateJsonInputInvalidPlayerId() {
        assertThrows(NonExistingEntityException.class, () -> betDtoValidator
                .validateBetJsonInput(BigDecimal.valueOf(10),-3L,2L));
    }
    @Test
    @DisplayName("validateJsonInput() method should throw an exception.")
    void validateJsonInputWithNullCasinoId() {
        assertThrows(NonExistingCasinoException.class, () -> betDtoValidator
                .validateBetJsonInput(BigDecimal.valueOf(10),3L,null));
    }
    @Test
    @DisplayName("validateJsonInput() method should throw an exception.")
    void validateJsonInputWithNullPlayerId() {
        assertThrows(NonExistingEntityException.class, () -> betDtoValidator
                .validateBetJsonInput(BigDecimal.valueOf(10),null,2L));
    }

}