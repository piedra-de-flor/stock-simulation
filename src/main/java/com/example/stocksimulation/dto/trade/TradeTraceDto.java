package com.example.stocksimulation.dto.trade;

import com.example.stocksimulation.domain.entity.TradeTrace;

import java.time.LocalDateTime;

public record TradeTraceDto(
        LocalDateTime dateTime,
        String stockName,
        String stockCode,
        long price,
        int quantity
) {
    public static TradeTraceDto create(TradeTrace trace) {
        return new TradeTraceDto(trace.getDate(),
        trace.getStockName(),
        trace.getStockCode(),
        trace.getPrice(),
        trace.getQuantity());
    }
}
