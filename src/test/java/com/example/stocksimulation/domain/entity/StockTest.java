package com.example.stocksimulation.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StockTest {
    @Test
    void 주식_가격_정보_업데이트_테스트() {
        Stock stock = new Stock();

        stock.updatePrice(1000);
        assertThat(stock.getPrice()).isEqualTo(1000);

        stock.updatePrice(5000);
        assertThat(stock.getPrice()).isEqualTo(5000);
    }
}