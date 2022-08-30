package com.example.jackpot.service.impl;

import com.example.jackpot.exception.InsufficientBalanceException;
import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Player;
import com.example.jackpot.repository.PlayerRepository;
import com.example.jackpot.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.jackpot.constants.LogErrorMessages.LOG_ERROR_MESSAGE;
import static com.example.jackpot.constants.PlayerMessages.*;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;

    }

    @Override
    public Optional<Player> getById(final Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public Player getByName(final String name) {
        return this.playerRepository.findByName(name).orElseThrow(() -> {
            throw new NonExistingEntityException(NON_EXISTING_PLAYER_BY_NAME);
        });
    }

    @Override
    public BigDecimal bet(final BigDecimal amount, final Long id) {
        Player player = playerRepository.findById(id).orElseThrow(
                () -> new NonExistingEntityException(NON_EXISTING_PLAYER_BY_ID)
        );
        if (player.getBalance().compareTo(amount) >= 0) {
            player.setBalance(player.getBalance().subtract(amount));
            playerRepository.update(player);
        } else {
            log.error(LOG_ERROR_MESSAGE);
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE);
        }
        return amount;
    }

    @Override
    public void increaseBalance(final BigDecimal playerBalance, final Long id) {
        Player player = playerRepository.findById(id).orElseThrow(
                () -> new NonExistingEntityException(NON_EXISTING_PLAYER_BY_ID)
        );
        BigDecimal currentBalance = player.getBalance();
        BigDecimal addedAmount = currentBalance.add(playerBalance);
        player.setBalance(addedAmount);
        playerRepository.update(player);
    }

    @Override
    public void payoutToPlayer(final Player player, final BigDecimal amount) {
        var playerBalance = player.getBalance();
        player.setBalance(playerBalance.add(amount));
        playerRepository.update(player);
    }
}
