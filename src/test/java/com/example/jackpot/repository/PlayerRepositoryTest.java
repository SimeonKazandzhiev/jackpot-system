package com.example.jackpot.repository;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Player;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import com.example.jackpot.util.DbInitializer;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerRepositoryTest {
    private static final Player PLAYER = new Player(new Random().nextLong(),
            BigDecimalRandomGenerator.generateRandom(), UUID.randomUUID().toString());

    @Test
    @DisplayName("findById() should return player.")
    void findById() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        jdbi.withExtension(PlayerRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(PLAYER);
            Player actual = repository.findById(PLAYER.getId())
                    .orElseThrow(NonExistingEntityException::new);
            assertThat(PLAYER.getId()).isEqualTo(actual.getId());
            return PLAYER;
        });
    }
    @Test
    @DisplayName("findByName() should return player.")
    void findByName() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        jdbi.withExtension(PlayerRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(PLAYER);
            Player actual = repository.findByName(PLAYER.getName())
                    .orElseThrow(NonExistingEntityException::new);
            assertThat(PLAYER.getName()).isEqualTo(actual.getName());
            return PLAYER;
        });
    }
    @Test
    @DisplayName("update() should update player return true.")
    void update() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        jdbi.withExtension(PlayerRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(PLAYER);
            assertTrue(repository.update(PLAYER));
            return PLAYER;
        });
    }
    @Test
    @DisplayName("create() should create player.")
    void create() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        jdbi.withExtension(PlayerRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            Player actual = repository.create(PLAYER);
            assertThat(actual.getId()).isEqualTo(PLAYER.getId());
            return PLAYER;
        });
    }
}