package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.dto.trade.TradeTraceDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TradeTrace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime date;
    private String stockCode;
    private String stockName;
    private long price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @Builder
    public TradeTrace(Trade trade) {
        this.date = LocalDateTime.now();
        this.account = trade.getAccount();
        this.stockCode = trade.getStockCode();
        this.stockName = trade.getStockName();
        this.price = trade.getStockPrice();
        this.quantity = trade.getQuantity();
        this.tradeType = trade.getTradeType();
    }
}
