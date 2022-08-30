package com.example.jackpot.manager;

import com.example.jackpot.model.Casino;

public interface CasinoLifeCycleManager {
    Casino updateCasinoUnderLock(Casino casino);

    Casino createCasinoUnderLock(Casino casino);

}
