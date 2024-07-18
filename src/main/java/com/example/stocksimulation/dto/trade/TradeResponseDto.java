package com.example.stocksimulation.dto.trade;

import com.example.stocksimulation.domain.vo.trade.Trade;

public record TradeResponseDto(
        String stockCode,
        int quantity
) {
    public static TradeResponseDto fromTrade(Trade trade) {
        return new TradeResponseDto(trade.getStockCode(), trade.getQuantity());
    }
}
