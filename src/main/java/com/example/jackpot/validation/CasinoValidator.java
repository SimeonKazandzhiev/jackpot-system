package com.example.jackpot.validation;

import com.example.jackpot.exception.CasinoUpdatingException;
import com.example.jackpot.exception.NonExistingCasinoException;
import com.example.jackpot.model.Casino;
import com.example.jackpot.service.CasinoService;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.jackpot.constants.CasinoMessages.NON_EXISTING_CASINO_BY_GIVEN_ID;
import static com.example.jackpot.constants.CasinoMessages.UPDATING_CASINO_NAME_MESSAGE;

@Component
public class CasinoValidator {

    private final CasinoService casinoService;

    public CasinoValidator(final CasinoService casinoService) {
        this.casinoService = casinoService;
    }

    public Casino validateCasinoIdGiven(final Long id) {
        Optional<Casino> casino = casinoService.findById(id);
        if (casino.isEmpty()) {
            throw new NonExistingCasinoException(NON_EXISTING_CASINO_BY_GIVEN_ID);
        }
        return casino.get();
    }

    public boolean validateNameGiven(final String casinoName, final String current) {
        if (!current.equals(casinoName)) {
            throw new CasinoUpdatingException(UPDATING_CASINO_NAME_MESSAGE);
        }
        return true;
    }
}
