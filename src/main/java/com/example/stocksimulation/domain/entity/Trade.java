package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.domain.vo.TradeType;
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

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @Builder
    public Trade(Account account, Stock stock, int quantity, TradeType tradeType) {
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
        this.tradeType = tradeType;
    }

    public void buy(int quantity) {
        this.quantity += quantity;
    }

    public void sell(int quantity) {
        this.quantity -= quantity;
    }

    public String getStockCode() {
        return stock.getCode();
    }

    public String getStockName() {
        return stock.getName();
    }

    public long getStockPrice() {
        return stock.getPrice();
    }
}
