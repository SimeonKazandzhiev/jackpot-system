package com.example.jackpot.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Player {

    private Long id;
    private BigDecimal balance;
    private String name;

    public Player() {
    }

    public Player(Long id, BigDecimal balance, String name) {
        this.id = id;
        this.balance = balance;
        this.name = name;
    }

    public Player(BigDecimal balance, String name) {
        this.balance = balance;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(balance, player.balance) && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}
