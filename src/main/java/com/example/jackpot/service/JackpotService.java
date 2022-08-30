package com.example.jackpot.service;

import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface JackpotService {
    Jackpot findById(Long id);

    Jackpot findByGivenCasinoId(Long id);

    Optional<Jackpot> createJackpot(Jackpot jackpot);

    boolean updateJackpot(Jackpot jackpot);

    List<Level> getAllLevels(Long id);

    boolean isWinning(Jackpot jackpot);

    boolean isInWinCondition(BigDecimal minAmountToWin, BigDecimal amount);

    List<Jackpot> getAllJackpots();
}
