package com.example.jackpot.validation;

import com.example.jackpot.exception.InvalidJackpotIdException;
import com.example.jackpot.exception.InvalidJackpotLevelsCountException;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JackpotValidatorTest {

    private JackpotValidator jackpotValidator;

    @BeforeEach
    void setup(){
        jackpotValidator = new JackpotValidator();
    }

    @Test
    @DisplayName("validateJackpot() method should pass.")
    void validateJackpot() {
        Jackpot jackpot = createJackpot();
        jackpot.setNumberOfLevels(1);
        jackpotValidator.validateGivenJackpotMatchLevelsCount(jackpot);
    }

    @Test
    @DisplayName("validateJackpotIdMatchGivenId() method should pass.")
    void validateJackpotIdMatchGivenId() {
        Long randomId = new Random().nextLong();
        jackpotValidator.validateJackpotIdMatchGivenId(randomId,randomId);
    }
    @Test
    @DisplayName("validateJackpotIdMatchGivenId() method throw an exception.")
    void validateJackpotIdMatchGivenIdInvalid() {
        Long randomId = new Random().nextLong();
        Long randomIdGiven = new Random().nextLong();
        assertThrows(InvalidJackpotIdException.class, () -> jackpotValidator.validateJackpotIdMatchGivenId(randomId,randomIdGiven));
    }
    @Test
    @DisplayName("validateJackpot() method should throw an exception.")
    void validateJackpotNumOfLevelsIncorrect() {
        Jackpot jackpot = createJackpot();
        assertThrows(InvalidJackpotLevelsCountException.class, () -> jackpotValidator.validateGivenJackpotMatchLevelsCount(jackpot));
    }

    private Jackpot createJackpot(){
        Long randomId = Math.abs(new Random().nextLong());
        return  new Jackpot(randomId,new Random().nextLong(), new Random().nextInt(), new Random().nextInt(),
                List.of(new Level(randomId,  BigDecimalRandomGenerator.generateRandom(),
                        BigDecimalRandomGenerator.generateRandom(),  BigDecimalRandomGenerator.generateRandom(),
                        BigDecimalRandomGenerator.generateRandom(), randomId)));
    }
}