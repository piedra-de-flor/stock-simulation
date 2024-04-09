package com.example.stocksimulation.dto.stock;

public record StockDto(
        String code,
        int price,
        String name
) {
}
