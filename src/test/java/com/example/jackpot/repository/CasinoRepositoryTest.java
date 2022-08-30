package com.example.jackpot.repository;

import com.example.jackpot.exception.NonExistingCasinoException;
import com.example.jackpot.model.Casino;
import com.example.jackpot.util.DbInitializer;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CasinoRepositoryTest {
    private static final Casino CASINO_ONE = new Casino(new Random().nextLong(), UUID.randomUUID().toString());
    private static final Casino CASINO_TWO = new Casino(new Random().nextLong(), UUID.randomUUID().toString());
    private static final List<Casino> CASINOS = List.of(CASINO_ONE,CASINO_TWO);

    @Test
    @DisplayName("findById() should return correct Casino.")
    void findById() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        jdbi.withExtension(CasinoRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(CASINO_ONE);
            Casino actual = repository.findById(CASINO_ONE.getId())
                    .orElseThrow(NonExistingCasinoException::new);

            assertThat(CASINO_ONE.getId()).isEqualTo(actual.getId());
            return CASINO_ONE;
        });
    }

    @Test
    @DisplayName("findByName() should return correct Casino.")
    void findByName() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        jdbi.withExtension(CasinoRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            repository.create(CASINO_ONE);
            Casino actual = repository.findById(CASINO_ONE.getId())
                    .orElseThrow(NonExistingCasinoException::new);

            assertThat(CASINO_ONE.getName()).isEqualTo(actual.getName());
            return CASINO_ONE;
        });
    }
    @Test
    @DisplayName("create() should create Casino")
    void create() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        jdbi.withExtension(CasinoRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            Long actualId = repository.create(CASINO_ONE);
            assertThat(actualId).isEqualTo(CASINO_ONE.getId());
            return CASINO_ONE;
        });
    }

    @Test
    @DisplayName("getAllCasinos() should return all casinos.")
    void getAllCasinos() throws IOException {
        Jdbi jdbi = DbInitializer.initializeDatabase();

        jdbi.withExtension(CasinoRepository.class, repository ->{
            jdbi.withHandle(handle -> handle.execute(DbInitializer.getScriptCreate()));
            CASINOS.forEach(repository::create);
            List<Casino> actualCasinos = repository.getAllCasinos();

            assertThat(actualCasinos.size()).isEqualTo(CASINOS.size());
            return CASINOS;
        });
    }
}