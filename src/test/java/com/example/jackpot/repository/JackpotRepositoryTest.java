package com.example.jackpot.repository;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import com.example.jackpot.util.DbInitializer;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JackpotRepositoryTest {

    @Test
    @DisplayName("findById() should return correct jackpot.")
    void findById() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        var jackpot = createJackpot();
        jackpot.setCasinoId(null);

        jdbi.withExtension(JackpotRepository.class,repository ->{
           jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
          var created = repository.create(jackpot.getCasinoId(),jackpot.getTriggerPercent(),
                   jackpot.getNumberOfLevels());

            Jackpot actualJackpot = repository.findById(created)
                    .orElseThrow(NonExistingEntityException::new);
            actualJackpot.setId(jackpot.getId());
            assertThat(actualJackpot.getId()).isEqualTo(jackpot.getId());
            return jackpot;
        });
    }

    @Test
    @DisplayName("create() should create jackpot.")
    void create() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        var jackpot = createJackpot();
        jackpot.setCasinoId(null);

        jdbi.withExtension(JackpotRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            Long actualId = repository.create(jackpot.getCasinoId(),
                    jackpot.getTriggerPercent(),jackpot.getNumberOfLevels());
            jackpot.setId(actualId);
            assertThat(actualId).isEqualTo(jackpot.getId());
            return jackpot;
        });
    }
    @Test
    @DisplayName("getAllJackpots() should return all jackpots.")
    void getAllJackpots() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        var jackpot = createJackpot();
        jackpot.setCasinoId(null);

        jdbi.withExtension(JackpotRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(jackpot.getCasinoId(),
                    jackpot.getTriggerPercent(),jackpot.getNumberOfLevels());
            List<Jackpot> actualJackpots = repository.getAllJackpots();

            assertThat(actualJackpots.size()).isEqualTo(1);
            return jackpot;
        });
    }
    @Test
    @DisplayName("update() should update jackpot.")
    void update() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        var jackpot = createJackpot();
        jackpot.setCasinoId(null);

        jdbi.withExtension(JackpotRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
           var actual = repository.create(jackpot.getCasinoId(),
                    jackpot.getTriggerPercent(),jackpot.getNumberOfLevels());
           jackpot.setId(actual);
            assertTrue(repository.update(jackpot.getCasinoId(),jackpot.getTriggerPercent(),
                    jackpot.getNumberOfLevels(),jackpot.getId()));
            return jackpot;
        });
    }

    private static Jackpot createJackpot() {
        Long randomId = new Random().nextLong();
        return new Jackpot(new Random().nextLong(), new Random().nextLong(),  new Random().nextInt(), new Random().nextInt(),
                List.of(new Level(randomId, BigDecimalRandomGenerator.generateRandom(),
                        BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                        BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(), randomId)));
    }
}