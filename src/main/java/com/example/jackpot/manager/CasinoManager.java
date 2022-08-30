package com.example.jackpot.manager;

import com.example.jackpot.model.Casino;

public interface CasinoManager {
    Casino createCasino(final Casino casino);

    Casino updateCasino(final Casino casino);
}
