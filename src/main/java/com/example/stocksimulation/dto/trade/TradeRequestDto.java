package com.example.stocksimulation.dto.trade;

public record TradeRequestDto(
        String stockCode,
        int quantity
) {
}
