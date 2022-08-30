package com.example.jackpot.service;

import com.example.jackpot.model.Player;

import java.math.BigDecimal;
import java.util.Optional;

public interface PlayerService {
    Optional<Player> getById(Long id);

    Player getByName(String name);

    BigDecimal bet(BigDecimal amount, Long id);

    void increaseBalance(BigDecimal playerBalance, Long id);

    void payoutToPlayer(Player player, BigDecimal amount);
}
