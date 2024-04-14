package com.example.stocksimulation.dto.stock;

public record StockDto(
        String code,
        long price,
        String name
) {
}
