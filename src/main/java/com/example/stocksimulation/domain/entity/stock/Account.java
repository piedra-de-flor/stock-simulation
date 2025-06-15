package com.example.stocksimulation.domain.entity.stock;

import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.vo.trade.Trade;
import com.example.stocksimulation.domain.vo.ZeroTradeQuantity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    private long money = 0;

    @OneToOne(mappedBy = "account")
    private Member member;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "trades", joinColumns = @JoinColumn(name = "account_id"))
    private List<Trade> trades = new ArrayList<>();

    @Builder
    public Account(Member member) {
        this.member = member;
    }

    public void buyTrade(long price, Trade trade) {
        buy(trade.getQuantity(), price);
        addNewTradeOrIncreaseTradeQuantity(trade);
    }

    public void sellTrade(long price, Trade trade) {
        Trade targetTrade = findTradeCanSell(trade.getStockCode(), trade.getQuantity());
        sell(trade.getQuantity(), price);
        targetTrade.sell(trade.getQuantity());
        deleteCompletedTradesAsync();
    }

    public void deposit(long money) {
        this.money += money;
    }

    public void withdraw(long money) {
        if (this.money >= money) {
            this.money -= money;
        } else {
            throw new IllegalArgumentException("not enough money");
        }
    }

    private void addNewTradeOrIncreaseTradeQuantity(Trade newTrade) {
        Optional<Trade> existingTrade = hasTrade(newTrade.getStockCode());
        if (existingTrade.isPresent()) {
            existingTrade.get().buy(newTrade.getQuantity());
        } else {
            trades.add(newTrade);
        }
    }

    private void buy(int quantity, long price) {
        long priceToPayment = quantity * price;

        if (this.money >= priceToPayment) {
            this.money -= priceToPayment;
        } else {
            throw new IllegalArgumentException("not enough money");
        }
    }

    private void sell(int quantity, long price) {
        this.money += quantity * price;
    }

    private Trade findTradeCanSell(String stockCode, int quantity) {
        Optional<Trade> candidateTrade = trades.stream().filter(trade -> trade.getStockCode().equals(stockCode)).findFirst();
        boolean response = candidateTrade.isPresent() && candidateTrade.get().getQuantity() >= quantity;

        if (response) {
            return candidateTrade.get();
        } else {
            throw new IllegalArgumentException("not enough stocks");
        }
    }

    private Optional<Trade> hasTrade(String stockCode) {
        return trades.stream().filter(trade -> trade.getStockCode().equals(stockCode)).findFirst();
    }

    @Async
    protected void deleteCompletedTradesAsync() {
        trades = trades.stream()
                .filter(trade -> trade.getQuantity() > ZeroTradeQuantity.EMPTY_TRADE.getQuantity())
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getHasTrades() {
        Map<String, Integer> hasTrades = new HashMap<>();

        for (Trade trade : trades) {
            hasTrades.put(trade.getStockCode(), trade.getQuantity());
        }

        return hasTrades;
    }
}
