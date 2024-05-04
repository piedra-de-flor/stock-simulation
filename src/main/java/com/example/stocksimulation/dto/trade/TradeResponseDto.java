package com.example.stocksimulation.dto.trade;

import com.example.stocksimulation.domain.entity.Trade;

public record TradeResponseDto(
        String stockName,
        int quantity
) {
    public static TradeResponseDto fromTrade(Trade trade) {
        return new TradeResponseDto(trade.getStock().getName(), trade.getQuantity());
    }
}
