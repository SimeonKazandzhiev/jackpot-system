package com.example.jackpot.handler.impl;

import com.example.jackpot.exception.NonExistingCasinoException;
import com.example.jackpot.handler.BetHandler;
import com.example.jackpot.manager.JackpotInstanceManager;
import com.example.jackpot.manager.impl.JackpotInstanceManagerImpl;
import com.example.jackpot.model.Casino;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.model.Player;
import com.example.jackpot.model.dto.BetDto;
import com.example.jackpot.service.JackpotService;
import com.example.jackpot.service.LevelService;
import com.example.jackpot.service.PlayerService;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import com.example.jackpot.validation.BetDtoValidator;
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
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BetHandlerImplTest {

    @Mock
    private BetDtoValidator betDtoValidator;
    @Mock
    private PlayerService playerService;
    @Mock
    private JackpotService jackpotService;
    @Mock
    private LevelService levelService;
    @Mock
    private Map<Long, JackpotInstanceManager> casinoIdsToJackpots;

    private BetHandler betHandler;

    @Test
    @DisplayName("bet() method should return the betDto.")
    void bet() {
        Player player = createPlayer();
        Casino casino = createMockCasino();
        betHandler = new BetHandlerImpl(playerService, jackpotService, levelService, betDtoValidator, casinoIdsToJackpots);
        BetDto betDto = new BetDto(BigDecimalRandomGenerator.generateRandom(),casino.getId(),player.getId());
        JackpotInstanceManager jackpotInstanceManager = new JackpotInstanceManagerImpl(casino.getJackpot(),playerService,jackpotService,levelService,
                new PlayerValidator());
        casinoIdsToJackpots.put(casino.getId(), jackpotInstanceManager);

        when(playerService.getById(player.getId())).thenReturn(Optional.of(player));
        when(casinoIdsToJackpots.get(any(Long.class))).thenReturn(jackpotInstanceManager);

        BetDto bet = betHandler.handleBetRequest(betDto);
        assertThat(bet).isNotNull().isEqualTo(betDto);
        verify(playerService).getById(player.getId());
        verify(casinoIdsToJackpots, atLeastOnce()).get(casino.getId());
    }

    @Test
    @DisplayName("bet() method should throw exception with not found casino.")
    void betNonExistingCasino() {
        betHandler = new BetHandlerImpl(playerService, jackpotService, levelService, betDtoValidator, casinoIdsToJackpots);
        BetDto betDto = new BetDto(BigDecimalRandomGenerator.generateRandom(),new Random().nextLong(),new Random().nextLong());

        assertThrows(NonExistingCasinoException.class, () ->
                betHandler.handleBetRequest(betDto));
    }

    @Test
    void loadJackpots(){
        Casino casino = createMockCasino();
        JackpotInstanceManager jackpotInstanceManager = new JackpotInstanceManagerImpl(casino.getJackpot(),playerService,jackpotService,levelService,
                new PlayerValidator());
        Map<Long,JackpotInstanceManager> jackpotInstanceManagerMap = new ConcurrentHashMap<>();
        jackpotInstanceManagerMap.put(casino.getId(),jackpotInstanceManager);

        betHandler = new BetHandlerImpl(playerService, jackpotService, levelService, betDtoValidator, jackpotInstanceManagerMap);

        assertThat(jackpotInstanceManagerMap.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("addJackpotToCache() should add the jackpot to cache map.")
    void addJackpotToCache(){
        Casino newCasino = createMockCasino();

        Map<Long,JackpotInstanceManager> jackpotInstanceManagerMap = new ConcurrentHashMap<>();

        betHandler = new BetHandlerImpl(playerService, jackpotService, levelService, betDtoValidator, jackpotInstanceManagerMap);
        betHandler.addJackpotInCache(newCasino);

        assertThat(jackpotInstanceManagerMap.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("updateJackpotInCache() should update the jackpot in cache map.")
    void updateJackpotInCache(){
        Casino casino = createMockCasino();

        JackpotInstanceManager jackpotInstanceManager = new JackpotInstanceManagerImpl(casino.getJackpot(),playerService,jackpotService,levelService,
                new PlayerValidator());
        Map<Long,JackpotInstanceManager> jackpotInstanceManagerMap = new ConcurrentHashMap<>();
        jackpotInstanceManagerMap.put(casino.getId(),jackpotInstanceManager);

        Jackpot jackpot = casino.getJackpot();
        jackpot.setTriggerPercent(7);
        casino.setJackpot(jackpot);

        betHandler = new BetHandlerImpl(playerService, jackpotService, levelService, betDtoValidator, jackpotInstanceManagerMap);
        betHandler.updateJackpotInCache(casino);

        JackpotInstanceManager actual = jackpotInstanceManagerMap.get(casino.getId());

        assertThat(actual.getJackpot().getTriggerPercent()).isEqualTo(7);

    }


    private Player createPlayer(){
        return new Player(new Random().nextLong(),BigDecimalRandomGenerator.generateRandom(), UUID.randomUUID().toString());
    }
    private Casino createMockCasino() {
        Long randomCasinoId = new Random().nextLong();
        Long randomJackpotId = new Random().nextLong();
        return new Casino(randomCasinoId, UUID.randomUUID().toString(),
                new Jackpot(randomJackpotId, randomCasinoId, new Random().nextInt(), new Random().nextInt(),
                        List.of(new Level(new Random().nextLong(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(), randomJackpotId))));
    }
}