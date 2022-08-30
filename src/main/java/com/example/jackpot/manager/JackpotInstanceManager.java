package com.example.jackpot.manager;

import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.dto.BetDto;

public interface JackpotInstanceManager {
    BetDto processBetForRequestedJackpot(BetDto bet);

    Jackpot getJackpot();
}
