package com.example.jackpot.validation;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.jackpot.constants.PlayerMessages.NON_EXISTING_PLAYER_BY_ID;

@Slf4j
@Component
public class PlayerValidator {
    public Optional<Player> validatePlayer(final Optional<Player> player) {
        if (player.isEmpty()) {
            log.error(NON_EXISTING_PLAYER_BY_ID);
            throw new NonExistingEntityException(NON_EXISTING_PLAYER_BY_ID);
        }
        return player;
    }
}
