package com.example.stocksimulation.dto.trade;

import java.time.LocalDateTime;

public record TradeTraceDto(
        LocalDateTime dateTime,
        String stockName,
        String stockCode,
        long price,
        int quantity
) {
}
