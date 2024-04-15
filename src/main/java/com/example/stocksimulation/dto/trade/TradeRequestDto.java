package com.example.stocksimulation.dto.trade;

public record TradeRequestDto(
        long accountId,
        String stockCode,
        int quantity
) {
}
