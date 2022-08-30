package com.example.jackpot.model;

import java.math.BigDecimal;

public class Bet {

    private BigDecimal amount;
    private Long casinoId;
    private Long playerId;

    public Bet(BigDecimal amount, Long casinoId, Long playerId) {
        this.amount = amount;
        this.casinoId = casinoId;
        this.playerId = playerId;
    }

    public Bet() {
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
