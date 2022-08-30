package com.example.jackpot.service.impl;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Level;
import com.example.jackpot.repository.LevelRepository;
import com.example.jackpot.service.LevelService;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LevelServiceImplTest {

    private LevelService levelService;
    private LevelRepository levelRepository;

    @BeforeEach
    void setup() {
        levelRepository = Mockito.mock(LevelRepository.class);
        levelService = new LevelServiceImpl(levelRepository);
    }

    @Test
    @DisplayName("GetById() method should return the correct Entity.")
    void getByIdShouldWork() {
        var level = createLevel();

        when(levelRepository.findById(any(Long.class))).thenReturn(Optional.of(level));

        var actual = levelService.getById(level.getId());

        assertEquals(level.getId(), actual.getId());
        verify(levelRepository).findById(level.getId());
    }

    @Test
    @DisplayName("GetById() method should throw exception.")
    void getByIdShouldThrowException() {
        long invalidId = 100L;

        when(levelRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NonExistingEntityException.class, () -> levelService.getById(invalidId));
        verify(levelRepository).findById(invalidId);
    }

    @Test
    @DisplayName("create() method should work properly.")
    void createLevelShouldWork() {
        var level = createLevel();
        when(levelRepository.create(any(Long.class), any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class),
                any(BigDecimal.class), any(BigDecimal.class), any(Long.class))).thenReturn(level.getId());
        when(levelRepository.findById(anyLong())).thenReturn(Optional.of(level));

        var actualLevel = levelService.createLevel(level);

        assertThat(actualLevel.getPercentOfBet()).isEqualTo(level.getPercentOfBet());
        assertThat(actualLevel.getWinProbability()).isEqualTo(level.getWinProbability());
        assertThat(actualLevel.getStartingAmount()).isEqualTo(level.getStartingAmount());
        assertThat(actualLevel.getMinimumAmountToBeWon()).isEqualTo(level.getMinimumAmountToBeWon());
        assertThat(actualLevel.getJackpotId()).isEqualTo(level.getJackpotId());

        verify(levelRepository).create(level.getId(), level.getTotalAmountCollected(), level.getPercentOfBet(),
                level.getStartingAmount(), level.getMinimumAmountToBeWon(), level.getWinProbability(), level.getJackpotId());
    }

    @Test
    @DisplayName("update should work properly and updating the levels.")
    void update() {
        var level = createLevel();

        when(levelRepository.findById(anyLong())).thenReturn(Optional.of(level));
        when(levelService.update(level))
                .thenReturn(true);

        var updated = levelService.update(level);

        assertTrue(updated);
        verify(levelRepository).update(level.getId(), level.getTotalAmountCollected(), level.getPercentOfBet(),
                level.getStartingAmount(), level.getMinimumAmountToBeWon(), level.getWinProbability(), level.getJackpotId());
    }

    @Test
    @DisplayName("update should throw an exception when non existing level given.")
    void updateShouldThrow() {
        var level = createLevel();

        when(levelRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatExceptionOfType(NonExistingEntityException.class)
                .isThrownBy(() -> {
                    levelService.update(level);
                });
        verify(levelRepository).findById(level.getId());
    }
    @Test
    void updateLevelsBatch() {
        var levelOne = createLevel();
        var levelTwo = createLevel();
        var levels = List.of(levelOne,levelTwo);

        List<BigDecimal> totalAmountsCollected = levels.stream().map(Level::getTotalAmountCollected).toList();
        List<BigDecimal> percentagesOfBet = levels.stream().map(Level::getPercentOfBet).toList();
        List<BigDecimal> startingAmounts = levels.stream().map(Level::getStartingAmount).toList();
        List<BigDecimal> minAmountsToBeWon = levels.stream().map(Level::getMinimumAmountToBeWon).toList();
        List<BigDecimal> winProbabilities = levels.stream().map(Level::getWinProbability).toList();
        List<Long> jackpotIds = levels.stream().map(Level::getJackpotId).toList();
        List<Long> ids = levels.stream().map(Level::getId).toList();

        levelService.updateLevelsBatch(levels);

        verify(levelRepository, times(1))
                .updateLevelsBatch(totalAmountsCollected, percentagesOfBet, startingAmounts, minAmountsToBeWon, winProbabilities, jackpotIds, ids);
    }

    @Test
    @DisplayName("getAllLevels() method should return correct collection of levels")
    void getAllLevels() {
        var levelOne = createLevel();
        var levelTwo = createLevel();
        var levels = List.of(levelOne,levelTwo);

        when(levelRepository.findAll()).thenReturn(levels);

        var actual = levelService.getAllLevels();

        assertEquals(levels, actual);
        verify(levelRepository).findAll();
    }

    @Test
    @DisplayName("getRandomWinningLevel() should return one winning level")
    void getRandomWinningLevel() {
        var levelOne = createLevel();
        var levelTwo = createLevel();
        var levels = List.of(levelOne,levelTwo);
        var randomWinningLevel = levelService.getRandomWinningLevel(levels);

        assertThat(randomWinningLevel).isNotNull();
        assertThat(randomWinningLevel).isIn(levels);
    }

    private Level createLevel(){
        Long randomId = new Random().nextLong();
        return new Level(randomId, BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                BigDecimalRandomGenerator.generateRandom(), randomId);
    }
}