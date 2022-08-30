package com.example.jackpot.handler;

import com.example.jackpot.model.Casino;
import com.example.jackpot.model.dto.BetDto;

public interface BetHandler {
    BetDto handleBetRequest(BetDto bet);

    void updateJackpotInCache(Casino casino);

    void addJackpotInCache(Casino casino);
}
