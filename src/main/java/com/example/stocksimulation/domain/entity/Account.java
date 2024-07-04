package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.ZeroTradeQuantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
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

    @OneToMany(mappedBy = "account")
    private List<TradeTrace> traces = new ArrayList<>();

    @Builder
    public Account(Member member) {
        this.member = member;
    }

    public void buy(Trade trade) {
        long priceToPayment = trade.getQuantity() * trade.getStock().getPrice();

        if (this.money >= priceToPayment) {
            this.money -= priceToPayment;
            this.trades.add(trade);
        } else {
            throw new IllegalArgumentException("not enough money");
        }
    }

    public void sell(Trade sellTrade) {
        List<Trade> sells = trades.stream()
                .filter(trade -> trade.getStock().getCode().equals(sellTrade.getStock().getCode()) &&
                        trade.getTradeType().equals(TradeType.BUY))
                .sorted(Comparator.comparingInt(Trade::getQuantity).reversed())
                .toList();

        int quantity = sellTrade.getQuantity();

        if (quantity > sells.get(0).getQuantity()) {
            throw new IllegalArgumentException("you don't have enough stocks");
        }

        for (Trade trade : sells) {
            if (quantity <= ZeroTradeQuantity.EMPTY_TRADE.getQuantity()) {
                break;
            }

            int tradeQuantity = trade.getQuantity();

            if (tradeQuantity >= quantity) {
                trade.sell(quantity);
                this.money += quantity * sellTrade.getStock().getPrice();
                quantity = ZeroTradeQuantity.EMPTY_TRADE.getQuantity();
            } else {
                this.money += tradeQuantity * sellTrade.getStock().getPrice();
                quantity -= tradeQuantity;
            }
        }
    }

    public long calculateBalance() {
        long balance = 0;

        for (Trade trade : trades) {
            balance += trade.getQuantity() * trade.getStock().getPrice();
        }

        return balance;
    }
}
