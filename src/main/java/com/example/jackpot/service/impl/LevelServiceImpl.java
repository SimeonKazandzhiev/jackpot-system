package com.example.jackpot.service.impl;

import com.example.jackpot.exception.NonExistingEntityException;
import com.example.jackpot.model.Level;
import com.example.jackpot.repository.LevelRepository;
import com.example.jackpot.service.LevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.jackpot.constants.LevelMessages.NON_EXISTING_LEVEL;
import static com.example.jackpot.constants.LogErrorMessages.LOG_ERROR_MESSAGE;

@Service
@Slf4j
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;

    public LevelServiceImpl(final LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public Level getById(final Long id) {
        return this.levelRepository.findById(id).orElseThrow(() -> {
            throw new NonExistingEntityException(NON_EXISTING_LEVEL);
        });
    }

    @Override
    public Collection<Level> getAllLevels() {
        return this.levelRepository.findAll();
    }

    @Override
    public Level createLevel(final Level level) {
        final long levelId = levelRepository.create(level.getId(), level.getTotalAmountCollected(),
                level.getPercentOfBet(), level.getStartingAmount(), level.getMinimumAmountToBeWon(),
                level.getWinProbability(), level.getJackpotId());
        level.setId(levelId);
        return level;
    }

    @Override
    public boolean update(final Level level) {
        Level old = getById(level.getId());
        if (old == null) {
            log.error(LOG_ERROR_MESSAGE);
            throw new NonExistingEntityException(NON_EXISTING_LEVEL);
        }
        return levelRepository.update(level.getId(), level.getTotalAmountCollected(),
                level.getPercentOfBet(), level.getStartingAmount(), level.getMinimumAmountToBeWon(),
                level.getWinProbability(), level.getJackpotId());
    }

    @Override
    public Level getRandomWinningLevel(final List<Level> levels) {
        BigDecimal mostWinProbability = new BigDecimal(0);
        List<BigDecimal> winProbabilities = new ArrayList<>();
        BigDecimal minWinProb = BigDecimal.valueOf(100);
        
        for (Level level : levels) {
            if (level.getWinProbability().compareTo(minWinProb) <= 0) {
                minWinProb = level.getWinProbability();
            }
            winProbabilities.add(level.getWinProbability());
            if (level.getWinProbability().compareTo(mostWinProbability) >= 0) {
                mostWinProbability = level.getWinProbability();
            }
        }
        Collections.sort(winProbabilities);

        int randomPercent = ThreadLocalRandom.current().nextInt(minWinProb.intValue(), mostWinProbability.intValue() + 1);
        BigDecimal matchedWinProbability = new BigDecimal(0);

        for (BigDecimal winProbability : winProbabilities) {
            if (winProbability.compareTo(BigDecimal.valueOf(randomPercent)) <= 0) {
                matchedWinProbability = winProbability;
            }
        }
        BigDecimal finalMatchedWinProbability = matchedWinProbability;

        return levels
                .stream()
                .filter(level -> level.getWinProbability().equals(finalMatchedWinProbability))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void updateLevelsBatch(final List<Level> levels) {
        getABatch(levels);
    }

    private void getABatch(final List<Level> levels) {
        List<BigDecimal> totalAmountCollected = new ArrayList<>();
        List<BigDecimal> percentagesOfBet = new ArrayList<>();
        List<BigDecimal> startingAmounts = new ArrayList<>();
        List<BigDecimal> minAmountsToBeWon = new ArrayList<>();
        List<BigDecimal> winProbabilities = new ArrayList<>();
        List<Long> jackpotIds = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        for (Level level : levels) {
            totalAmountCollected.add(level.getTotalAmountCollected());
            percentagesOfBet.add(level.getPercentOfBet());
            startingAmounts.add(level.getStartingAmount());
            minAmountsToBeWon.add(level.getMinimumAmountToBeWon());
            winProbabilities.add(level.getWinProbability());
            jackpotIds.add(level.getJackpotId());
            ids.add(level.getId());
        }

        levelRepository.updateLevelsBatch(totalAmountCollected, percentagesOfBet, startingAmounts,
                minAmountsToBeWon, winProbabilities, jackpotIds, ids);
    }
}


