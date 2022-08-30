package com.example.jackpot.service.impl;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.repository.JackpotRepository;
import com.example.jackpot.service.JackpotService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JackpotServiceImplTest {

    private JackpotService jackpotService;
    private JackpotRepository jackpotRepository;

    @BeforeEach
    void setup() {
        jackpotRepository = Mockito.mock(JackpotRepository.class);
        jackpotService = new JackpotServiceImpl(jackpotRepository);
    }

    @Test
    @DisplayName("GetById() method should return the correct Entity.")
    void findByIdShouldWorkProperly() {
        var jackpot = createJackpot();

        when(jackpotRepository.findById(any(Long.class))).thenReturn(Optional.of(jackpot));

        var actual = jackpotService.findById(jackpot.getId());

        assertEquals(jackpot.getId(), actual.getId());
        verify(jackpotRepository).findById(jackpot.getId());
    }

    @Test
    @DisplayName("GetById() should throw exception")
    void findByIdShouldThrow() {
        long invalidId = 100L;

        when(jackpotRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NonExistingEntityException.class, () -> jackpotService.findById(invalidId));
        verify(jackpotRepository).findById(invalidId);
    }

    @Test
    @DisplayName("The method findByGivenCasinoId() should work correctly.")
    void findByGivenCasinoId() {
        var jackpot = createJackpot();
        when(jackpotRepository.findJackpotByCasinoId(any(Long.class))).thenReturn(Optional.of(jackpot));

        var actual = jackpotService.findByGivenCasinoId(jackpot.getId());

        assertEquals(jackpot.getId(), actual.getId());
        verify(jackpotRepository).findJackpotByCasinoId(jackpot.getId());
    }

    @Test
    @DisplayName("The method findByGivenCasinoId() should throw exception.")
    void findByGivenCasinoIdShouldThrow() {
        long invalidId = 100L;

        when(jackpotRepository.findJackpotByCasinoId(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NonExistingEntityException.class, () ->
                jackpotService.findByGivenCasinoId(invalidId));
        verify(jackpotRepository).findJackpotByCasinoId(invalidId);
    }

    @Test
    @DisplayName("getAllLevels() method should return collection with expected values")
    void getAllLevels() {
        var jackpot = createJackpot();

        when(jackpotRepository.getAllLevelsForJackpot(anyLong())).thenReturn(jackpot.getLevels());

        var actualLevels = jackpotService.getAllLevels(jackpot.getId());

        assertThat(jackpot.getLevels()).isEqualTo(actualLevels);
        verify(jackpotRepository).getAllLevelsForJackpot(jackpot.getId());
    }

    @Test
    @DisplayName("getAllJackpots() method should return collection with expected values")
    void getAllJackpots() {
        var jackpotOne = createJackpot();
        var jackpotTwo = createJackpot();
        List<Jackpot> jackpots = List.of(jackpotOne,jackpotTwo);

        when(jackpotRepository.getAllJackpots()).thenReturn(jackpots);

        var actualJackpots = jackpotService.getAllJackpots();

        assertThat(jackpots).isEqualTo(actualJackpots);
        verify(jackpotRepository).getAllJackpots();
    }

    @Test
    @DisplayName("isInWinCondition() method should return false with given params")
    void isInWinConditionReturnFalse() {
        assertFalse(jackpotService.isInWinCondition(BigDecimal.valueOf(4), BigDecimal.valueOf(3)));
    }

    @Test
    @DisplayName("isInWinCondition() method should return true with given params")
    void isInWinConditionReturnTrue() {
        assertTrue(jackpotService.isInWinCondition(BigDecimal.valueOf(3), BigDecimal.valueOf(4)));
    }

    private Jackpot createJackpot(){
        Long randomId = new Random().nextLong();
        return  new Jackpot(randomId,new Random().nextLong(), new Random().nextInt(), new Random().nextInt(),
                List.of(new Level(new Random().nextLong(),  BigDecimalRandomGenerator.generateRandom(),
                        BigDecimalRandomGenerator.generateRandom(),  BigDecimalRandomGenerator.generateRandom(),
                        BigDecimalRandomGenerator.generateRandom(), randomId)));
    }
}
