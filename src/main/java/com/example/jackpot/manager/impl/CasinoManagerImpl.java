package com.example.jackpot.manager.impl;


import com.example.jackpot.exception.CasinoCreationException;
import com.example.jackpot.manager.CasinoLifeCycleManager;
import com.example.jackpot.manager.CasinoManager;
import com.example.jackpot.model.Casino;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CasinoManagerImpl implements CasinoManager {
    private final CasinoLifeCycleManager casinoLifeCycleManager;

    public CasinoManagerImpl(final CasinoLifeCycleManager casinoLifeCycleManager) {
        this.casinoLifeCycleManager = casinoLifeCycleManager;
    }

    @Override
    @Transactional
    public Casino createCasino(final Casino casino) {
        if (casino == null) {
            throw new CasinoCreationException();
        }
        return this.casinoLifeCycleManager.createCasinoUnderLock(casino);
    }

    @Override
    public Casino updateCasino(final Casino casino) {
        return this.casinoLifeCycleManager.updateCasinoUnderLock(casino);
    }
}
