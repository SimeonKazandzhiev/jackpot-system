package com.example.jackpot.manager.impl;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.manager.JackpotInstanceManager;
import com.example.jackpot.model.Bet;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.model.Player;
import com.example.jackpot.model.dto.BetDto;
import com.example.jackpot.service.JackpotService;
import com.example.jackpot.service.LevelService;
import com.example.jackpot.service.PlayerService;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import com.example.jackpot.validation.PlayerValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class JackpotInstanceManagerImplTest {

    @Mock
    private PlayerService playerService;
    @Mock
    private JackpotService jackpotService;
    @Mock
    private LevelService levelService;
    @Mock
    private PlayerValidator playerValidator;

    private JackpotInstanceManager jackpotInstanceManager;


    @Test
    @DisplayName("handleBetForAJackpot() method should return the same dto and call the needed services.")
    void handleBetForAJackpot() {
        Jackpot jackpot = createJackpot();
        jackpotInstanceManager = new JackpotInstanceManagerImpl(jackpot, playerService, jackpotService, levelService,
                playerValidator);
        Long randomId = new Random().nextLong();
        Player player = new Player(randomId,BigDecimalRandomGenerator.generateRandom(), UUID.randomUUID().toString());
        BetDto dto = new BetDto(BigDecimalRandomGenerator.generateRandom(),new Random().nextLong(),randomId);
        Bet bet = new Bet(dto.getAmount(), dto.getCasinoId(), randomId);

        when(playerService.getById(player.getId())).thenReturn(Optional.of(player));
        when(jackpotService.isWinning(jackpot)).thenReturn(false);

        BetDto actual = jackpotInstanceManager.processBetForRequestedJackpot(dto);

        assertThat(actual).isNotNull();
        assertThat(actual.getAmount()).isEqualTo(bet.getAmount());
        assertThat(actual.getCasinoId()).isEqualTo(bet.getCasinoId());
        assertThat(actual.getPlayerId()).isEqualTo(bet.getPlayerId());

        verify(playerService).getById(player.getId());
        verify(playerService).bet( actual.getAmount(),player.getId());
    }

    @Test
    @DisplayName("handleBetForAJackpot() method should throw an exception with non existing player.")
    void handleBetNonExistingPlayer() {
        Jackpot jackpot = createJackpot();
        jackpotInstanceManager = new JackpotInstanceManagerImpl(jackpot, playerService, jackpotService, levelService,
                playerValidator);
        Player player = new Player(new Random().nextLong(),BigDecimalRandomGenerator.generateRandom(),UUID.randomUUID().toString());

        when(playerValidator.validatePlayer(Optional.of(player))).thenThrow(NonExistingEntityException.class);
        assertThrows(NonExistingEntityException.class, () ->
                playerValidator.validatePlayer(Optional.of(player)));
    }

    @Test
    void updateLevelsInCache() {
        Jackpot jackpot = createJackpot();
        Map<Long, Level> levelsMockCache = new ConcurrentHashMap<>();
        levelsMockCache.put(jackpot.getLevels().get(0).getId(), jackpot.getLevels().get(0));
        jackpotInstanceManager = new JackpotInstanceManagerImpl(jackpot, playerService, jackpotService, levelService,
                playerValidator);

        assertThat(levelsMockCache.get(jackpot.getId()).getTotalAmountCollected().doubleValue())
                .isEqualTo(jackpot.getLevels().get(0).getTotalAmountCollected().doubleValue());
    }

    private Jackpot createJackpot(){
        Long randomId = new Random().nextLong();
        return  new Jackpot(randomId,new Random().nextLong(), new Random().nextInt(), new Random().nextInt(),
                List.of(new Level(randomId,BigDecimalRandomGenerator.generateRandom() , BigDecimalRandomGenerator.generateRandom(),
                        BigDecimalRandomGenerator.generateRandom(),  BigDecimalRandomGenerator.generateRandom(),
                        BigDecimalRandomGenerator.generateRandom(), randomId)));
    }
}