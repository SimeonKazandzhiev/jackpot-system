package com.example.jackpot.manager.impl;

import com.example.jackpot.handler.BetHandler;
import com.example.jackpot.manager.CasinoLifeCycleManager;
import com.example.jackpot.manager.JackpotInstanceManager;
import com.example.jackpot.model.Casino;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.service.CasinoService;
import com.example.jackpot.service.JackpotService;
import com.example.jackpot.service.LevelService;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import com.example.jackpot.validation.CasinoValidator;
import com.example.jackpot.validation.JackpotValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CasinoLifeCycleManagerImplTest {

    private static final Long randomCasinoId = new Random().nextLong();
    private static final Long randomJackpotId = new Random().nextLong();

    @Mock
    private CasinoService casinoService;
    @Mock
    private JackpotService jackpotService;
    @Mock
    private LevelService levelService;
    @Mock
    private BetHandler betHandler;
    @Mock
    private JackpotValidator jackpotValidator;
    @Mock
    private CasinoValidator casinoValidator;
    @Mock
    private Map<Long, JackpotInstanceManager> casinoIdsToJackpots;

    private CasinoLifeCycleManager casinoLifeCycleManager;

    @Test
    @DisplayName("createCasino() should create casino.")
    void createCasino() {
        casinoLifeCycleManager = new CasinoLifeCycleManagerImpl(casinoService, jackpotService, levelService, betHandler,
                jackpotValidator, casinoValidator, casinoIdsToJackpots);
        Casino casino = createMockCasino();
        Jackpot jackpot = casino.getJackpot();

        when(casinoService.create(any(Casino.class))).thenReturn(casino);
        when(jackpotService.createJackpot(any(Jackpot.class))).thenReturn(Optional.ofNullable(jackpot));
        var actual = casinoLifeCycleManager.createCasinoUnderLock(casino);

        assertThat(actual).isNotNull().isEqualTo(casino);

        verify(casinoService).create(casino);
        verify(jackpotService).createJackpot(jackpot);
    }

    @Test
    @DisplayName("updateCasino() should update the given casino")
    void updateCasino() {
        casinoLifeCycleManager = new CasinoLifeCycleManagerImpl(casinoService, jackpotService, levelService, betHandler,
                jackpotValidator, casinoValidator, casinoIdsToJackpots);
        Casino casino = createMockCasino();
        Casino casinoUpdated = new Casino(randomCasinoId, UUID.randomUUID().toString(),
                new Jackpot(randomJackpotId, randomCasinoId, new Random().nextInt(), 1,
                        List.of(new Level(new Random().nextLong(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(), randomJackpotId))));

        when(casinoValidator.validateCasinoIdGiven(casino.getId())).thenReturn(casino);
        when(casinoValidator.validateNameGiven(any(String.class),any(String.class))).thenReturn(true);
        when(jackpotService.updateJackpot(any(Jackpot.class))).thenReturn(true);

        var actual = casinoLifeCycleManager.updateCasinoUnderLock(casinoUpdated);

        assertThat(actual).isNotNull().isEqualTo(casino);

        verify(jackpotService).updateJackpot(casino.getJackpot());
    }

    private Casino createMockCasino() {
        return new Casino(randomCasinoId, UUID.randomUUID().toString(),
                new Jackpot(randomJackpotId, randomCasinoId, new Random().nextInt(), new Random().nextInt(),
                        List.of(new Level(new Random().nextLong(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(), randomJackpotId))));
    }
}