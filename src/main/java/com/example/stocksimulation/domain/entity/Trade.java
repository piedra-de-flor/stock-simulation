package com.example.stocksimulation.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private int quantity;

    @Builder
    public Trade(Account account, Stock stock, int quantity) {
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
    }

    public void sell(int quantity) {
        this.quantity -= quantity;
    }
}
