package com.example.jackpot.manager.impl;

import com.example.jackpot.exception.CasinoCreationException;
import com.example.jackpot.manager.CasinoLifeCycleManager;
import com.example.jackpot.manager.CasinoManager;
import com.example.jackpot.model.Casino;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CasinoManagerImplTest {

    @Mock
    private CasinoLifeCycleManager casinoLifeCycleManager;
    private CasinoManager casinoManager;

    @BeforeEach
    void setup() {
        casinoManager = new CasinoManagerImpl(casinoLifeCycleManager);
    }

    @Test
    @DisplayName("createCasino() should work correctly.")
    void createCasino() {
        var casino = createMockCasino();

        when(casinoManager.createCasino(casino)).thenReturn(casino);

        var actual = casinoManager.createCasino(casino);
        assertThat(actual).isNotNull().isEqualTo(casino);

        verify(casinoLifeCycleManager).createCasinoUnderLock(casino);
    }

    @Test
    @DisplayName("createCasino() should throw exception.")
    void createCasinoShouldThrow() {
        Casino casino = null;
        assertThrows(CasinoCreationException.class, () -> casinoManager
                .createCasino(casino));
    }

    @Test
    @DisplayName("updateCasino() should work properly.")
    void updateCasino() {
        var casino = createMockCasino();
        var jackpot = casino.getJackpot();
        casino.setJackpot(jackpot);
        jackpot.setLevels(casino.getJackpot().getLevels());

        when(casinoManager.updateCasino(casino)).thenReturn(casino);
        Casino returned = casinoManager.updateCasino(casino);

        assertThat(casino.getId()).isEqualTo(returned.getId());
        assertThat(casino.getJackpot().getTriggerPercent()).isEqualTo(returned.getJackpot().getTriggerPercent());

        verify(casinoLifeCycleManager).updateCasinoUnderLock(casino);
    }


    private Casino createMockCasino() {
        Long randomCasinoId = new Random().nextLong();
        Long randomJackpotId = new Random().nextLong();
        return new Casino(randomCasinoId, UUID.randomUUID().toString(),
                new Jackpot(randomJackpotId, randomCasinoId, new Random().nextInt(), new Random().nextInt(),
                        List.of(new Level(new Random().nextLong(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                randomJackpotId))));
    }
}

