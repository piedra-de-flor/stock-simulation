package com.example.stocksimulation.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class StockDetail {
    private long startPrice;
    private long endPrice;
    private long highPrice;
    private long lowPrice;
    private int tradeCount;
    private long fluctuationRate;
}
