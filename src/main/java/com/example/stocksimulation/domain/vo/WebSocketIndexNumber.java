package com.example.stocksimulation.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebSocketIndexNumber {
    INDEX_OF_RESPONSE(3),
    INDEX_OF_CODE(0),
    INDEX_OF_PRICE(2);

    private final int value;
}
