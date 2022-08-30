package com.example.jackpot.model;

public class Casino {

    private Long id;
    private String name;
    private Jackpot jackpot;

    public Casino() {
    }

    public Casino(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Casino(Long id, String name, Jackpot jackpot) {
        this.id = id;
        this.name = name;
        this.jackpot = jackpot;
    }

    public Casino(String name, Jackpot jackpot) {
        this.name = name;
        this.jackpot = jackpot;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Jackpot getJackpot() {
        return jackpot;
    }

    public void setJackpot(Jackpot jackpot) {
        this.jackpot = jackpot;
    }
}
