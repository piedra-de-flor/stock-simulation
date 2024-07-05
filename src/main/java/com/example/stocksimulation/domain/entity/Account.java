package com.example.stocksimulation.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long money = 0;

    @OneToOne(mappedBy = "account")
    private Member member;

    @OneToMany(mappedBy = "account")
    private List<Trade> trades = new ArrayList<>();

    @Builder
    public Account(Member member) {
        this.member = member;
    }

    public void buy(int quantity, long price) {
        long priceToPayment = quantity * price;

        if (this.money >= priceToPayment) {
            this.money -= priceToPayment;
        } else {
            throw new IllegalArgumentException("not enough money");
        }
    }

    public void sell(int quantity, long price) {
        this.money += quantity * price;
    }

    public boolean canSell(String stockCode, int quantity) {
        return trades.stream()
                .anyMatch(trade -> trade.getStockCode().equals(stockCode)) &&
                trades.stream()
                        .filter(trade -> trade.getStockCode().equals(stockCode))
                        .findFirst().get().getQuantity() >= quantity;
    }

    public boolean hasTrade(String stockCode) {
        return trades.stream().anyMatch(trade -> trade.getStockCode().equals(stockCode));
    }

    public long calculateBalance() {
        long balance = 0;

        for (Trade trade : trades) {
            balance += trade.getQuantity() * trade.getStock().getPrice();
        }

        return balance;
    }
}
