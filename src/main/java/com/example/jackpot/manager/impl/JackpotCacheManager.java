package com.example.jackpot.manager.impl;

import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JackpotCacheManager {

    public static Jackpot updateLevelsInCache(final BigDecimal amount, final Jackpot jackpot) {
        final List<Level> levels = jackpot.getLevels();
        for (Level level : levels) {
            BigDecimal levelPercentageOfBet = level.getPercentOfBet();
            BigDecimal amountToBeAdd = levelPercentageOfBet.multiply(amount).multiply(BigDecimal.valueOf(0.01));
            level.setTotalAmountCollected(level.getTotalAmountCollected().add(amountToBeAdd));
        }
        return jackpot;
    }
    public static void recoverLevelsInCache(final Jackpot jackpot) {
        final List<Level> levels = jackpot.getLevels();
        for (Level level : levels) {
            level.setTotalAmountCollected(level.getStartingAmount());
        }
    }
}
