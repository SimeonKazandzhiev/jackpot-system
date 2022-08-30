package com.example.jackpot.handler;

import com.example.jackpot.model.dto.BetDto;

public interface ResponseHandler {
    void notifyUser(final String id, BetDto bet);
}
