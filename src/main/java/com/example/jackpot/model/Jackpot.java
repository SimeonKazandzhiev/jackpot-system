package com.example.jackpot.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Jackpot {

    private Long id;
    private Long casinoId;
    private Integer triggerPercent;
    private Integer numberOfLevels;
    private List<Level> levels = new ArrayList<>();

    public Jackpot() {
    }

    public Jackpot(Long id, Long casinoId, Integer triggerPercent, Integer numberOfLevels) {
        this.id = id;
        this.casinoId = casinoId;
        this.triggerPercent = triggerPercent;
        this.numberOfLevels = numberOfLevels;
    }

    public Jackpot(Long id, Long casinoId, Integer triggerPercent, Integer numberOfLevels, List<Level> levels) {
        this.id = id;
        this.casinoId = casinoId;
        this.triggerPercent = triggerPercent;
        this.numberOfLevels = numberOfLevels;
        this.levels = levels;
    }

    public Jackpot(Long casinoId, Integer triggerPercent, Integer numberOfLevels, List<Level> levels) {
        this.casinoId = casinoId;
        this.triggerPercent = triggerPercent;
        this.numberOfLevels = numberOfLevels;
        this.levels = levels;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getCasinoId() {
        return casinoId;
    }

    public void setCasinoId(Long casinoId) {
        this.casinoId = casinoId;
    }

    public Integer getTriggerPercent() {
        return triggerPercent;
    }

    public void setTriggerPercent(Integer triggerPercent) {
        this.triggerPercent = triggerPercent;
    }

    public Integer getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(Integer numberOfLevels) {
        this.numberOfLevels = numberOfLevels;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jackpot that = (Jackpot) o;
        return Objects.equals(id, that.id) && Objects.equals(casinoId, that.casinoId) && Objects.equals(triggerPercent, that.triggerPercent) && Objects.equals(numberOfLevels, that.numberOfLevels) && Objects.equals(levels, that.levels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, casinoId, triggerPercent, numberOfLevels, levels);
    }

    @Override
    public String toString() {
        return "Jackpot{" +
                "id=" + id +
                ", casinoId=" + casinoId +
                ", triggerPercent=" + triggerPercent +
                ", numberOfLevels=" + numberOfLevels +
                ", levels=" + levels +
                '}';
    }
}
