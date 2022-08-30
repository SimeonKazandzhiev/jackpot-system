package com.example.jackpot.service.impl;

import com.example.jackpot.config.RandomConfig;
import com.example.jackpot.exception.JackpotUpdatingException;
import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.repository.JackpotRepository;
import com.example.jackpot.service.JackpotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.jackpot.constants.JackpotMessages.*;
import static com.example.jackpot.constants.LogErrorMessages.LOG_ERROR_MESSAGE;

@Service
@Slf4j
public class JackpotServiceImpl implements JackpotService {
    private final JackpotRepository jackpotRepository;

    public JackpotServiceImpl(final JackpotRepository jackpotRepository) {
        this.jackpotRepository = jackpotRepository;
    }

    @Override
    public Jackpot findById(final Long id) {
        return this.jackpotRepository.findById(id).orElseThrow(() ->
                new NonExistingEntityException(NON_EXISTING_JACKPOT_BY_ID));
    }

    @Override
    public Jackpot findByGivenCasinoId(final Long id) {
        return this.jackpotRepository.findJackpotByCasinoId(id).orElseThrow(() ->
                new NonExistingEntityException(NON_EXISTING_JACKPOT_BY_CASINO_ID));
    }

    @Override
    public Optional<Jackpot> createJackpot(final Jackpot jackpot) {
        long jackpotId = jackpotRepository.create(jackpot.getCasinoId(), jackpot.getTriggerPercent(),
                jackpot.getNumberOfLevels());
        var created = new Jackpot(jackpot.getId(), jackpot.getCasinoId(), jackpot.getTriggerPercent(), jackpot.getNumberOfLevels());
        created.setLevels(getAllLevels(jackpotId));
        created.setId(jackpotId);
        return Optional.of(created);
    }

    @Override
    public boolean updateJackpot(final Jackpot jackpot) {
        return jackpotRepository.update(jackpot.getCasinoId(), jackpot.getTriggerPercent(),
                jackpot.getNumberOfLevels(), jackpot.getId());
    }

    @Override
    public List<Level> getAllLevels(Long id) {
        return this.jackpotRepository.getAllLevelsForJackpot(id);
    }

    @Override
    public boolean isWinning(final Jackpot jackpot) {
        final int triggerPercent = jackpot.getTriggerPercent();
        final int randomPercent = ThreadLocalRandom.current().nextInt(RandomConfig.getProperties());
        return randomPercent <= triggerPercent;
    }

    @Override
    public boolean isInWinCondition(final BigDecimal minAmountToWin, final BigDecimal amount) {
        return minAmountToWin.compareTo(amount) <= 0;
    }

    @Override
    public List<Jackpot> getAllJackpots() {
        final List<Jackpot> jackpots = this.jackpotRepository.getAllJackpots();
        for (Jackpot jackpot : jackpots) {
            jackpot.setLevels(getAllLevels(jackpot.getId()));
        }
        return jackpots;
    }
}