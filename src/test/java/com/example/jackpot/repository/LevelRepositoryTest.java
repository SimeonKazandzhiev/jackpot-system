package com.example.jackpot.repository;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Level;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import com.example.jackpot.util.DbInitializer;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelRepositoryTest {
    private static final Level LEVEL_ONE =
            new Level(new Random().nextLong(),
                    BigDecimalRandomGenerator.generateRandom(),
                    BigDecimalRandomGenerator.generateRandom(),
                    BigDecimalRandomGenerator.generateRandom(),
                    BigDecimalRandomGenerator.generateRandom(),
                    BigDecimalRandomGenerator.generateRandom(), new Random().nextLong());

    @Test
    @DisplayName("findById() should return the level.")
    void findById() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();
        LEVEL_ONE.setJackpotId(null);

        jdbi.withExtension(LevelRepository.class, repository -> {
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(LEVEL_ONE.getId(), LEVEL_ONE.getTotalAmountCollected(),
                    LEVEL_ONE.getPercentOfBet(), LEVEL_ONE.getStartingAmount(),
                    LEVEL_ONE.getMinimumAmountToBeWon(), LEVEL_ONE.getWinProbability(),
                    LEVEL_ONE.getJackpotId());
            var actual = repository.findById(LEVEL_ONE.getId())
                    .orElseThrow(NonExistingEntityException::new);

            assertThat(LEVEL_ONE.getId()).isEqualTo(actual.getId());
            return LEVEL_ONE;
        });
    }

    @Test
    @DisplayName("create() should create level.")
    void create() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();
        LEVEL_ONE.setJackpotId(null);

        jdbi.withExtension(LevelRepository.class, repository -> {
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            Long actualId = repository.create(LEVEL_ONE.getId(), LEVEL_ONE.getTotalAmountCollected(),
                    LEVEL_ONE.getPercentOfBet(), LEVEL_ONE.getStartingAmount(),
                    LEVEL_ONE.getMinimumAmountToBeWon(), LEVEL_ONE.getWinProbability(),
                    LEVEL_ONE.getJackpotId());
            assertThat(actualId).isEqualTo(LEVEL_ONE.getId());
            return LEVEL_ONE;
        });
    }

    @Test
    @DisplayName("findAll() should return all levels.")
    void findAll() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();
        LEVEL_ONE.setJackpotId(null);

        jdbi.withExtension(LevelRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(LEVEL_ONE.getId(), LEVEL_ONE.getTotalAmountCollected(),
                    LEVEL_ONE.getPercentOfBet(), LEVEL_ONE.getStartingAmount(),
                    LEVEL_ONE.getMinimumAmountToBeWon(), LEVEL_ONE.getWinProbability(),
                    LEVEL_ONE.getJackpotId());
            Collection<Level> actualLevels = repository.findAll();

            assertThat(actualLevels.size()).isEqualTo(1);
            return LEVEL_ONE;
        });
    }
    @Test
    @DisplayName("update() should work correctly.")
    void update() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();
        var level = LEVEL_ONE;

        level.setJackpotId(null);
        jdbi.withExtension(LevelRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(LEVEL_ONE.getId(), LEVEL_ONE.getTotalAmountCollected(),
                    LEVEL_ONE.getPercentOfBet(), LEVEL_ONE.getStartingAmount(),
                    LEVEL_ONE.getMinimumAmountToBeWon(), LEVEL_ONE.getWinProbability(),
                    LEVEL_ONE.getJackpotId());
            assertTrue(repository.update(level.getId(),level.getTotalAmountCollected(),
                    level.getPercentOfBet(),level.getStartingAmount(),level.getMinimumAmountToBeWon(),
                    level.getWinProbability(),level.getJackpotId()));
            return level;
        });
    }
}