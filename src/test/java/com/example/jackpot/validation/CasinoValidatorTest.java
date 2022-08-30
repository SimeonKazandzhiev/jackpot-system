package com.example.jackpot.validation;

import com.example.jackpot.exception.CasinoUpdatingException;
import com.example.jackpot.exception.NonExistingCasinoException;
import com.example.jackpot.model.Casino;
import com.example.jackpot.service.CasinoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CasinoValidatorTest {
    private static final Casino CASINO_ONE = new Casino(new Random().nextLong(), UUID.randomUUID().toString());
    @Mock
    private  CasinoService casinoService;
    private CasinoValidator casinoValidator;

    @Test
    @DisplayName("validation should pass.")
    void validateCasinoIdGiven() {
        casinoValidator = new CasinoValidator(casinoService);

        when(casinoService.findById(any(Long.class))).thenReturn(Optional.of(CASINO_ONE));

        var actual = casinoValidator.validateCasinoIdGiven(CASINO_ONE.getId());

        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("validation should throw an exception.")
    void validateCasinoIdGivenThrows() {
        casinoValidator = new CasinoValidator(casinoService);

        when(casinoService.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatExceptionOfType(NonExistingCasinoException.class)
                .isThrownBy(() -> {
                    casinoValidator.validateCasinoIdGiven(CASINO_ONE.getId());
                });
    }

    @Test
    @DisplayName("validation should pass.")
    void validateNameGiven() {
        casinoValidator = new CasinoValidator(casinoService);

        String name = CASINO_ONE.getName();
        var actual = casinoValidator.validateNameGiven(CASINO_ONE.getName(),name);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("validation should throw an exception.")
    void validateNameGivenThrows() {
        casinoValidator = new CasinoValidator(casinoService);

        String name = UUID.randomUUID().toString();

        assertThatExceptionOfType(CasinoUpdatingException.class)
                .isThrownBy(() -> {
                    casinoValidator.validateNameGiven(CASINO_ONE.getName(),name);
                });
    }
}