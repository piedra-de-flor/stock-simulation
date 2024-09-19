package com.example.stocksimulation.dto.stock;

public record StockDetailDto (long startPrice,
        long endPrice,
        long highPrice,
        long lowPrice,
        int tradeCount,
        long fluctuationRate) {
}
