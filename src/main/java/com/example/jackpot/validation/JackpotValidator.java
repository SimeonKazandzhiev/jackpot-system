package com.example.jackpot.validation;

import com.example.jackpot.exception.InvalidJackpotIdException;
import com.example.jackpot.exception.InvalidJackpotLevelsCountException;
import com.example.jackpot.model.Jackpot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.jackpot.constants.JackpotMessages.*;
import static com.example.jackpot.constants.LogErrorMessages.LOG_ERROR_MESSAGE;


@Slf4j
@Component
public class JackpotValidator {

    public void validateGivenJackpotMatchLevelsCount(Jackpot jackpot) {
        if (jackpot.getLevels().size() != jackpot.getNumberOfLevels()) {
            log.error(FORBIDDEN_CHANGE_OF_LEVELS_COUNT);
            throw new InvalidJackpotLevelsCountException(INVALID_COUNT_OF_LEVELS);
        }
    }

    public void validateJackpotIdMatchGivenId(final Long jackpotId, final Long id) {
        if (!id.equals(jackpotId)) {
            log.error(LOG_ERROR_MESSAGE);
            throw new InvalidJackpotIdException(String.format(UPDATING_JACKPOT_LEVELS_JSON, id, jackpotId));
        }
    }
}
