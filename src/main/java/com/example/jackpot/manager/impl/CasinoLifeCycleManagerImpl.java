package com.example.jackpot.manager.impl;

import com.example.jackpot.executor.LockExecutor;
import com.example.jackpot.handler.BetHandler;
import com.example.jackpot.manager.CasinoLifeCycleManager;
import com.example.jackpot.manager.JackpotInstanceManager;
import com.example.jackpot.model.Casino;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.service.CasinoService;
import com.example.jackpot.service.JackpotService;
import com.example.jackpot.service.LevelService;
import com.example.jackpot.validation.CasinoValidator;
import com.example.jackpot.validation.JackpotValidator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.jackpot.config.SchedulerTimingRates.PUSH_TO_DB_RATE;

@Component
@EnableScheduling
public class CasinoLifeCycleManagerImpl implements CasinoLifeCycleManager {

    private final CasinoService casinoService;
    private final JackpotService jackpotService;
    private final LevelService levelService;
    private final BetHandler betHandler;
    private final JackpotValidator jackpotValidator;
    private final CasinoValidator casinoValidator;
    private final LockExecutor lockExecutor;
    private final Map<Long,JackpotInstanceManager> casinoIdsToJackpots;

    public CasinoLifeCycleManagerImpl(final CasinoService casinoService, final JackpotService jackpotService, final LevelService levelService,
                                      final BetHandler betHandler, final JackpotValidator jackpotValidator, final CasinoValidator casinoValidator, final Map<Long, JackpotInstanceManager> casinoIdsToJackpots) {
        this.casinoService = casinoService;
        this.jackpotService = jackpotService;
        this.levelService = levelService;
        this.betHandler = betHandler;
        this.jackpotValidator = jackpotValidator;
        this.casinoValidator = casinoValidator;
        this.casinoIdsToJackpots = casinoIdsToJackpots;
        this.lockExecutor = new LockExecutor();
    }

    @Override
    public Casino updateCasinoUnderLock(final Casino casino) {
        return this.lockExecutor.executeUnderLock(casino,this::updateCasino);
    }

    @Override
    public Casino createCasinoUnderLock(final Casino casino) {
        return this.lockExecutor.executeUnderLock(casino,this::createCasino);
    }

    private Casino updateCasino(final Casino casino){
        final Casino current = casinoValidator.validateCasinoIdGiven(casino.getId());
        casinoValidator.validateNameGiven(current.getName(), casino.getName());
        Jackpot jackpot = casino.getJackpot();

        updateJackpotForCasino(jackpot, casino.getId());
        current.setJackpot(casino.getJackpot());
        betHandler.updateJackpotInCache(casino);

        return current;
    }

    private Casino createCasino(final Casino casino){
        final Casino created = casinoService.create(casino);
        final Jackpot jackpot = created.getJackpot();
        jackpot.setCasinoId(created.getId());

        createJackpotForCasino(jackpot);
        betHandler.addJackpotInCache(casino);

        return created;
    }

    private Jackpot createJackpotForCasino(final Jackpot jackpot) {
        jackpotValidator.validateGivenJackpotMatchLevelsCount(jackpot);

        var created = jackpotService
                .createJackpot(jackpot);

        final List<Level> levels = jackpot.getLevels();
        createLevelsForJackpot(created, levels);

        return created.get();
    }

    private Jackpot updateJackpotForCasino(final Jackpot jackpot, final Long id) {
        jackpotValidator.validateGivenJackpotMatchLevelsCount(jackpot);
        jackpotValidator.validateJackpotIdMatchGivenId(jackpot.getId(), id);

        jackpotService.updateJackpot(jackpot);

        return jackpot;
    }


    private void createLevelsForJackpot(final Optional<Jackpot> created, final List<Level> levels) {
        for (Level level : levels) {
            level.setJackpotId(created.get().getId());
            levelService.createLevel(level);
        }
    }
    private void updateJackpotsInDbUnderLock(final List<JackpotInstanceManager> jackpots){
        lockExecutor.executeUnderLock(jackpots, this::updateJackpotLevelsAmountToDB);
    }


    private List<JackpotInstanceManager> updateJackpotLevelsAmountToDB(final List<JackpotInstanceManager> jackpots) {
        List<Level> levels = new ArrayList<>();
        for (JackpotInstanceManager jackpot : jackpots) {
            levels.addAll(jackpot.getJackpot().getLevels());
        }
        levelService.updateLevelsBatch(levels);
        return jackpots;
    }

    @Scheduled(fixedRateString = PUSH_TO_DB_RATE)
    public void scheduledUpdateDb() {
        final List<JackpotInstanceManager> jackpotInstances = casinoIdsToJackpots.values().stream().toList();
        updateJackpotsInDbUnderLock(jackpotInstances);
    }
}
