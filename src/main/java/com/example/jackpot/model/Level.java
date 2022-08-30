package com.example.jackpot.model;

import java.math.BigDecimal;
import java.util.Objects;


public class Level {

    private Long id;
    private BigDecimal totalAmountCollected;
    private BigDecimal percentOfBet;
    private BigDecimal startingAmount;
    private BigDecimal minimumAmountToBeWon;
    private BigDecimal winProbability;
    private Long jackpotId;

    public Level() {
    }

    public Level(Long id, BigDecimal totalAmountCollected, BigDecimal percentOfBet, BigDecimal startingAmount, BigDecimal minimumAmountToBeWon, BigDecimal winProbability, Long jackpotId) {
        this.id = id;
        this.totalAmountCollected = totalAmountCollected;
        this.percentOfBet = percentOfBet;
        this.startingAmount = startingAmount;
        this.minimumAmountToBeWon = minimumAmountToBeWon;
        this.winProbability = winProbability;
        this.jackpotId = jackpotId;
    }

    public Level(BigDecimal totalAmountCollected, BigDecimal percentOfBet, BigDecimal startingAmount, BigDecimal minimumAmountToBeWon, BigDecimal winProbability, Long jackpotId) {
        this.totalAmountCollected = totalAmountCollected;
        this.percentOfBet = percentOfBet;
        this.startingAmount = startingAmount;
        this.minimumAmountToBeWon = minimumAmountToBeWon;
        this.winProbability = winProbability;
        this.jackpotId = jackpotId;
    }

    public Level(Long id, BigDecimal percentOfBet, BigDecimal startingAmount, BigDecimal minimumAmountToBeWon, BigDecimal winProbability, Long jackpotId) {
        this.id = id;
        this.percentOfBet = percentOfBet;
        this.startingAmount = startingAmount;
        this.minimumAmountToBeWon = minimumAmountToBeWon;
        this.winProbability = winProbability;
        this.jackpotId = jackpotId;
    }

    public Level(BigDecimal percentOfBet, BigDecimal startingAmount, BigDecimal minimumAmountToBeWon, BigDecimal winProbability) {
        this.percentOfBet = percentOfBet;
        this.startingAmount = startingAmount;
        this.minimumAmountToBeWon = minimumAmountToBeWon;
        this.winProbability = winProbability;
    }


    public BigDecimal getTotalAmountCollected() {
        return totalAmountCollected;
    }

    public void setTotalAmountCollected(BigDecimal totalAmountCollected) {
        this.totalAmountCollected = totalAmountCollected;
    }

    public Long getJackpotId() {
        return jackpotId;
    }

    public void setJackpotId(Long jackpotId) {
        this.jackpotId = jackpotId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPercentOfBet() {
        return percentOfBet;
    }

    public void setPercentOfBet(BigDecimal percentOfBet) {
        this.percentOfBet = percentOfBet;
    }

    public BigDecimal getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(BigDecimal startingAmount) {
        this.startingAmount = startingAmount;
    }

    public BigDecimal getMinimumAmountToBeWon() {
        return minimumAmountToBeWon;
    }

    public void setMinimumAmountToBeWon(BigDecimal minimumAmountToBeWon) {
        this.minimumAmountToBeWon = minimumAmountToBeWon;
    }

    public BigDecimal getWinProbability() {
        return winProbability;
    }

    public void setWinProbability(BigDecimal winProbability) {
        this.winProbability = winProbability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return Objects.equals(id, level.id) && Objects.equals(percentOfBet, level.percentOfBet) && Objects.equals(startingAmount, level.startingAmount) && Objects.equals(minimumAmountToBeWon, level.minimumAmountToBeWon) && Objects.equals(winProbability, level.winProbability) && Objects.equals(jackpotId, level.jackpotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, percentOfBet, startingAmount, minimumAmountToBeWon, winProbability, jackpotId);
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", percentOfBet=" + percentOfBet +
                ", startingAmount=" + startingAmount +
                ", minimumAmountToBeWon=" + minimumAmountToBeWon +
                ", winProbability=" + winProbability +
                ", jackpotConfigId=" + jackpotId +
                '}';
    }


}
