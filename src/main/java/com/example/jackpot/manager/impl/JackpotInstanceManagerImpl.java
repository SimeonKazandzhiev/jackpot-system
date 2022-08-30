package com.example.jackpot.manager.impl;

import com.example.jackpot.manager.JackpotInstanceManager;
import com.example.jackpot.model.Bet;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Player;
import com.example.jackpot.model.dto.BetDto;
import com.example.jackpot.service.JackpotService;
import com.example.jackpot.service.LevelService;
import com.example.jackpot.service.PlayerService;
import com.example.jackpot.validation.PlayerValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
public class JackpotInstanceManagerImpl implements JackpotInstanceManager {

    private final Jackpot jackpot;
    private final PlayerService playerService;
    private final JackpotService jackpotService;
    private final LevelService levelService;
    private final PlayerValidator playerValidator;
    private final AtomicReference<Jackpot> atomicReference;
    private final JackpotConditionManager jackpotConditionManager;

    public JackpotInstanceManagerImpl(final Jackpot jackpot, final PlayerService playerService,
                                      final JackpotService jackpotService, final LevelService levelService,
                                      final PlayerValidator playerValidator) {
        this.playerValidator = playerValidator;
        this.jackpot = jackpot;
        this.playerService = playerService;
        this.jackpotService = jackpotService;
        this.levelService = levelService;
        this.atomicReference = new AtomicReference<>(this.jackpot);
        this.jackpotConditionManager = new JackpotConditionManager(levelService, jackpotService, playerService);
    }

    @Override
    public BetDto processBetForRequestedJackpot(final BetDto betDto) {
        Optional<Player> player = getPlayer(betDto.getPlayerId());
        playerValidator.validatePlayer(player);

        final Bet bet = new Bet(betDto.getAmount(), betDto.getCasinoId(), betDto.getPlayerId());
        playerService.bet(bet.getAmount(), player.get().getId());
        updateJackpotAtomically(bet);

        jackpotConditionManager.checkIfPlayerWinning(bet, getJackpot(), player.get());

        return betDto;
    }

    @Override
    public Jackpot getJackpot() {
        return atomicReference.get();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final JackpotInstanceManagerImpl that = (JackpotInstanceManagerImpl) o;
        return Objects.equals(jackpot, that.jackpot) && Objects.equals(playerService, that.playerService) && Objects.equals(jackpotService, that.jackpotService) && Objects.equals(levelService, that.levelService) && Objects.equals(playerValidator, that.playerValidator) && Objects.equals(atomicReference, that.atomicReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jackpot, playerService, jackpotService, levelService, playerValidator, atomicReference);
    }

    private void updateJackpotAtomically(Bet bet) {
        var existingJackpotValue = getJackpot();
        var newJackpotValue = JackpotCacheManager.updateLevelsInCache(bet.getAmount(), jackpot);
        atomicReference.compareAndSet(existingJackpotValue, newJackpotValue);
    }

    private Optional<Player> getPlayer(final Long id) {
        return playerService.getById(id);
    }
}
