package com.example.jackpot.model.dto;

import java.math.BigDecimal;

public class BetDto {
    private BigDecimal amount;
    private Long casinoId;
    private Long playerId;

    public BetDto() {
    }

    public BetDto(BigDecimal amount, Long casinoId, Long playerId) {
        this.amount = amount;
        this.casinoId = casinoId;
        this.playerId = playerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCasinoId() {
        return casinoId;
    }

    public void setCasinoId(Long casinoId) {
        this.casinoId = casinoId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
