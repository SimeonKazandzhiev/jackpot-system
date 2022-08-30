package com.example.jackpot.service.impl;

import com.example.jackpot.model.Casino;
import com.example.jackpot.repository.CasinoRepository;
import com.example.jackpot.service.CasinoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CasinoServiceImplTest {
    private static final Casino CASINO_ONE = new Casino(new Random().nextLong(), UUID.randomUUID().toString());
    private static final Casino CASINO_TWO = new Casino(new Random().nextLong(),UUID.randomUUID().toString());
    private CasinoService casinoService;
    private CasinoRepository casinoRepository;

    @BeforeEach
    void setup() {
        casinoRepository = Mockito.mock(CasinoRepository.class);
        casinoService = new CasinoServiceImpl(casinoRepository);
    }

    @Test
    @DisplayName("findById() method should return correct entity.")
    void findByIdShouldWork() {
        var casino = CASINO_ONE;

        when(casinoRepository.findById(any(Long.class))).thenReturn(Optional.of(casino));

        var actual = casinoService.findById(casino.getId());

        assertEquals(casino.getId(), actual.get().getId());
        verify(casinoRepository).findById(casino.getId());
    }

    @Test
    @DisplayName("findByName() method should return correct entity.")
    void findByNameShouldWorkProperly() {
        var casino = CASINO_TWO;

        when(casinoRepository.findByName(any(String.class))).thenReturn(Optional.of(casino));

        var actual = casinoService.findByName(casino.getName());

        assertEquals(casino.getName(), actual.get().getName());
        verify(casinoRepository).findByName(casino.getName());
    }
}