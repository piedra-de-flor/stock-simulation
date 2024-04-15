package com.example.stocksimulation.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TradeVO {
    EMPTY_TRADE(0);

    private final int quantity;
}
