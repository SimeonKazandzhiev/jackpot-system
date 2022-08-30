package com.example.jackpot.service.impl;

import com.example.jackpot.exception.InsufficientBalanceException;
import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Player;
import com.example.jackpot.repository.PlayerRepository;
import com.example.jackpot.service.PlayerService;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlayerServiceImplTest {

    private PlayerService playerService;
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        playerRepository = Mockito.mock(PlayerRepository.class);
        playerService = new PlayerServiceImpl(playerRepository);
    }

    @Test
    @DisplayName("GetById() method should return the correct Entity.")
    void getByIdShouldWorkProperly() {
        var player = createPlayer();

        when(playerRepository.findById(any(Long.class))).thenReturn(Optional.of(player));

        var actual = playerService.getById(player.getId());

        assertEquals(player.getId(), actual.get().getId());
        verify(playerRepository).findById(player.getId());
    }

    @Test
    @DisplayName("GetByName() method should thrown exception.")
    void getByNameShouldThrowException() {
        String wrongName = UUID.randomUUID().toString();

        when(playerRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        assertThrows(NonExistingEntityException.class, () ->
                playerService.getByName(wrongName));
    }

    @Test
    @DisplayName("GetByName() method should return correct Entity.")
    void getByNameShouldReturnCorrectEntity() {
        Player player = createPlayer();
        String playerName = player.getName();

        when(playerRepository.findByName(any(String.class))).thenReturn(Optional.of(player));

        Player actual = playerService.getByName(playerName);

        assertEquals(actual.getName(), playerName);
        verify(playerRepository).findByName(playerName);
    }

    @Test
    @DisplayName("Bet method should throw exception when id is invalid")
    void betShouldThrow() {
        var wrongId = 100L;
        assertThrows(NonExistingEntityException.class,
                () -> playerService.bet(BigDecimalRandomGenerator.generateRandom(), wrongId));
    }

    @Test
    @DisplayName("Bet method should work properly when bet is made.")
    void betShouldWorkProperly() {
        var player = createPlayer();
        player.setBalance(BigDecimal.valueOf(1000));
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));
        BigDecimal betAmount = BigDecimal.valueOf(200);

        playerService.bet(betAmount, player.getId());

        var afterBetBalance = player.getBalance();

        assertEquals(player.getBalance(), afterBetBalance);
        verify(playerRepository).findById(player.getId());
    }

    @Test
    @DisplayName("Bet method should throw when balance is not enough for bet.")
    void betShouldThrowWhenBalanceNotEnough() {
        var player = createPlayer();
        var amount = BigDecimal.valueOf(100_000_000);
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        assertThrows(InsufficientBalanceException.class,
                () -> playerService.bet(amount, player.getId()));
    }

    @Test
    @DisplayName("Increase method should work properly")
    void increaseBalance() {
        var player = createPlayer();

        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        var increaseValue = BigDecimalRandomGenerator.generateRandom();
        playerService.increaseBalance(increaseValue, player.getId());
        var afterIncrease = player.getBalance();

        assertEquals(player.getBalance(), afterIncrease);
        verify(playerRepository).findById(player.getId());
    }

    @Test
    @DisplayName("Increase method should throw exception when invalid id is given.")
    void increaseBalanceThrow() {
        var wrongId = new Random().nextLong();
        assertThrows(NonExistingEntityException.class,
                () -> playerService.increaseBalance(BigDecimalRandomGenerator.generateRandom(), wrongId));
    }

    @Test
    @DisplayName("Payout method should work properly")
    void payoutToPlayer() {
        var player = createPlayer();

        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        var payoutValue = BigDecimalRandomGenerator.generateRandom();
        playerService.payoutToPlayer(player, payoutValue);
        var afterPayout = player.getBalance();

        assertEquals(player.getBalance(), afterPayout);
        verify(playerRepository).update(player);
    }

    private Player createPlayer() {
        return new Player(new Random().nextLong(), BigDecimalRandomGenerator.generateRandom(), UUID.randomUUID().toString());
    }
}