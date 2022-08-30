package com.example.jackpot.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class ResponseDto {

    private String casinoName;
    private List<BigDecimal> levelAmounts;
    private Long playerId;
    private BigDecimal playerBalance;

    public ResponseDto() {
    }

    public ResponseDto(String casinoName, List<BigDecimal> levelAmounts, Long playerId, BigDecimal playerBalance) {
        this.casinoName = casinoName;
        this.levelAmounts = levelAmounts;
        this.playerId = playerId;
        this.playerBalance = playerBalance;
    }

    public String getCasinoName() {
        return casinoName;
    }

    public void setCasinoName(String casinoName) {
        this.casinoName = casinoName;
    }

    public List<BigDecimal> getLevelAmounts() {
        return levelAmounts;
    }

    public void setLevelAmounts(List<BigDecimal> levelAmounts) {
        this.levelAmounts = levelAmounts;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public BigDecimal getPlayerBalance() {
        return playerBalance;
    }

    public void setPlayerBalance(BigDecimal playerBalance) {
        this.playerBalance = playerBalance;
    }
}
