package com.example.jackpot.validation;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Player;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.example.jackpot.constants.PlayerMessages.NON_EXISTING_PLAYER_BY_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlayerValidatorTest {

    private PlayerValidator playerValidator;

    @Test
    @DisplayName("validation should pass.")
    void validatePlayer() {
        playerValidator = new PlayerValidator();
        Player player = new Player(new Random().nextLong(), BigDecimalRandomGenerator.generateRandom(), UUID.randomUUID().toString());

        Optional<Player> actual = playerValidator.validatePlayer(Optional.of(player));

        assertThat(actual.get()).isNotNull().isEqualTo(player);

    }

    @Test
    @DisplayName("validation should throw an exception.")
    void validatePlayerThrows() {
        playerValidator = new PlayerValidator();
        assertThatExceptionOfType(NonExistingEntityException.class)
                .isThrownBy(() -> {
                    playerValidator.validatePlayer(Optional.empty());
                }).withMessage(NON_EXISTING_PLAYER_BY_ID);

    }
}