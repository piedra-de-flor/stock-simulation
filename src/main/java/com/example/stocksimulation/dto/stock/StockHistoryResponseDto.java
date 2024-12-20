package com.example.stocksimulation.dto.stock;

import java.time.Instant;

public record StockHistoryResponseDto(String stockCode, double price, Instant time){}
