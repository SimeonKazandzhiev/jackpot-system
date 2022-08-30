package com.example.jackpot.manager.impl;

import com.example.jackpot.model.Bet;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.model.Player;
import com.example.jackpot.service.JackpotService;
import com.example.jackpot.service.LevelService;
import com.example.jackpot.service.PlayerService;
import org.springframework.stereotype.Component;

@Component
public class JackpotConditionManager {

    private final LevelService levelService;
    private final JackpotService jackpotService;
    private final PlayerService playerService;

    public JackpotConditionManager(LevelService levelService, JackpotService jackpotService, PlayerService playerService) {
        this.levelService = levelService;
        this.jackpotService = jackpotService;
        this.playerService = playerService;
    }

    public void checkIfPlayerWinning(final Bet bet, final Jackpot jackpot, final Player player) {
        boolean isJackpotWinning = jackpotService.isWinning(jackpot);
        if (isJackpotWinning) {
            Level randomPickedWinningLevel = levelService.getRandomWinningLevel(jackpot.getLevels());
            boolean isMinAmountToWinMet = jackpotService.isInWinCondition(randomPickedWinningLevel.getMinimumAmountToBeWon(),
                    randomPickedWinningLevel.getTotalAmountCollected());
            if(isMinAmountToWinMet){
                playerService.payoutToPlayer(player, randomPickedWinningLevel.getTotalAmountCollected().add(bet.getAmount()));
                JackpotCacheManager.recoverLevelsInCache(jackpot);
            }
        }
    }
}
