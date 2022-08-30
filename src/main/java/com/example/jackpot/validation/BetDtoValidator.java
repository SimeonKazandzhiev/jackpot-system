package com.example.jackpot.validation;

import com.example.jackpot.exception.BetAmountException;
import com.example.jackpot.exception.NonExistingCasinoException;
import com.example.jackpot.exception.NonExistingEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Slf4j
public class BetDtoValidator {
    private static final String BET_AMOUNT_MESSAGE = "The given amount cannot be negative number";
    private static final String BET_MESSAGE_NULL = "The id given cannot be null.";
    private static final String BET_MESSAGE_NEGATIVE = "The id must be positive number.";

    public void validateBetJsonInput(final BigDecimal amount, final Long playerId, final Long casinoId){
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            log.error(BET_AMOUNT_MESSAGE);
            throw new BetAmountException(BET_AMOUNT_MESSAGE);
        }
        if (playerId == null) {
            log.error(BET_MESSAGE_NULL);
            throw new NonExistingEntityException(BET_MESSAGE_NULL);
        }
        if(playerId < 0){
            log.error(BET_MESSAGE_NEGATIVE);
            throw new NonExistingEntityException(BET_MESSAGE_NEGATIVE);
        }
        if (casinoId == null) {
            log.error(BET_MESSAGE_NULL);
            throw new NonExistingCasinoException(BET_MESSAGE_NULL);
        }
        if (casinoId < 0) {
            log.error(BET_MESSAGE_NEGATIVE);
            throw new NonExistingCasinoException(BET_MESSAGE_NEGATIVE);
        }
    }

}
