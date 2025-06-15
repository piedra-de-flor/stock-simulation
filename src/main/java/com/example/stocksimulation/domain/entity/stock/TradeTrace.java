package com.example.stocksimulation.domain.entity.stock;

import com.example.stocksimulation.domain.vo.TradeType;
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
    public TradeTrace(Account account, Stock stock, int quantity, TradeType tradeType) {
        this.date = LocalDateTime.now();
        this.account = account;
        this.stockCode = stock.getCode();
        this.stockName = stock.getName();
        this.price = stock.getPrice();
        this.quantity = quantity;
        this.tradeType = tradeType;
    }
}
