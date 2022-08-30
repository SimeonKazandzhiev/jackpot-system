package com.example.jackpot.handler.impl;

import com.example.jackpot.exception.NonExistingCasinoException;
import com.example.jackpot.handler.BetHandler;
import com.example.jackpot.manager.JackpotInstanceManager;
import com.example.jackpot.manager.impl.JackpotInstanceManagerImpl;
import com.example.jackpot.model.Casino;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.model.dto.BetDto;
import com.example.jackpot.service.JackpotService;
import com.example.jackpot.service.LevelService;
import com.example.jackpot.service.PlayerService;
import com.example.jackpot.validation.BetDtoValidator;
import com.example.jackpot.validation.PlayerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.example.jackpot.constants.CasinoMessages.NON_EXISTING_CASINO_BY_GIVEN_ID;

@Component
@Slf4j
public class BetHandlerImpl implements BetHandler {
    private final BetDtoValidator betDtoValidator;
    private final PlayerService playerService;
    private final JackpotService jackpotService;
    private final LevelService levelService;
    private final Map<Long, JackpotInstanceManager> casinoIdsToJackpots;

    public BetHandlerImpl(final PlayerService playerService,
                          final JackpotService jackpotService, final LevelService levelService,
                          final BetDtoValidator betDtoValidator, final Map<Long, JackpotInstanceManager> casinoIdsToJackpots) {
        this.playerService = playerService;
        this.jackpotService = jackpotService;
        this.levelService = levelService;
        this.betDtoValidator = betDtoValidator;
        this.casinoIdsToJackpots = casinoIdsToJackpots;
        loadJackpots();
    }

    @Override
    public BetDto handleBetRequest(final BetDto bet) {
        betDtoValidator.validateBetJsonInput(bet.getAmount(), bet.getPlayerId(), bet.getCasinoId());
        final JackpotInstanceManager jackpot = casinoIdsToJackpots.get(bet.getCasinoId());

        if (jackpot == null) {
            log.error(NON_EXISTING_CASINO_BY_GIVEN_ID);
            throw new NonExistingCasinoException(NON_EXISTING_CASINO_BY_GIVEN_ID);
        }
        return jackpot.processBetForRequestedJackpot(bet);
    }

    @Override
    public void updateJackpotInCache(final Casino casino) {
        Jackpot jackpot = casinoIdsToJackpots.get(casino.getId()).getJackpot();
        jackpot.setTriggerPercent(casino.getJackpot().getTriggerPercent());
        List<Level> levels = casino.getJackpot().getLevels();
        jackpot.setLevels(levels);
    }

    @Override
    public void addJackpotInCache(final Casino casino) {
        casinoIdsToJackpots
                .put(casino.getId(), new JackpotInstanceManagerImpl(casino.getJackpot(),
                        playerService, jackpotService, levelService, new PlayerValidator()));
    }

    private void loadJackpots() {
        final List<Jackpot> allJackpots = jackpotService.getAllJackpots();

        for (Jackpot jackpot : allJackpots) {
            var jackpotInstance =
                    new JackpotInstanceManagerImpl(jackpot, playerService, jackpotService, levelService,
                            new PlayerValidator());
            casinoIdsToJackpots
                    .putIfAbsent(jackpot.getCasinoId(),
                            jackpotInstance);
        }
    }
}


