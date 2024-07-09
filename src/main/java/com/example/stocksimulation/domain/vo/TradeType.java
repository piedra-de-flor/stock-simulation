package com.example.stocksimulation.domain.vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TradeType {
    BUY("B"),
    SELL("S");

    private final String type;
}
