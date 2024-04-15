package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.TradeVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long money;

    @OneToOne(mappedBy = "account")
    private Member member;

    @OneToMany(mappedBy = "account")
    private List<Trade> trades;

    @OneToMany(mappedBy = "account")
    private List<TradeTrace> traces;

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

        for (Trade trade : sells) {
            if (quantity <= TradeVO.EMPTY_TRADE.getQuantity()) {
                break;
            }

            int tradeQuantity = trade.getQuantity();

            if (tradeQuantity >= quantity) {
                trade.sell(quantity);
                this.money += quantity * sellTrade.getStock().getPrice();
                quantity = TradeVO.EMPTY_TRADE.getQuantity();
            } else {
                this.money += tradeQuantity * sellTrade.getStock().getPrice();
                quantity -= tradeQuantity;
            }
        }
    }
}
