/*
package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.domain.vo.TradeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {
    private Account account;
    private Stock stock;

    @BeforeEach
    void setUp() {
        account = new Account();
        stock = new Stock("TEST_CODE", 5000, "test");
    }

    @Test
    void 매수_성공_테스트() {
        int testQuantity = 10;

        account.buy(testQuantity, stock.getPrice());

        assertEquals(-stock.getPrice() * testQuantity, account.getMoney());
        assertEquals(1, account.getTrades().size());
    }

    @Test
    void 매도_성공_테스트() {
        int buyQuantity = 10;
        int sellQuantity = 5;

        account.sell("test", sellQuantity, stock.getPrice());

        assertEquals(-stock.getPrice() * buyQuantity + stock.getPrice() * sellQuantity, account.getMoney());
        assertEquals(buyQuantity - sellQuantity, account.getTrades().get(0).getQuantity());
    }

    @Test
    void 매도_실패_테스트_불충분한_주식() {
        String testCode = "test";
        int testQuantity = 10;

        Assertions.assertThrows(IllegalArgumentException.class, () -> account.sell(testCode, testQuantity, 5000));
    }
}*/
