package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.domain.vo.TradeType;
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
        long testPrice = 5000;
        Trade trade = new Trade(account, stock, testQuantity, TradeType.BUY);

        account.buy(trade);

        assertEquals(500000 - testPrice * testQuantity, account.getMoney());
        assertEquals(1, account.getTrades().size());
    }

    @Test
    void 매도_성공_테스트() {
        int buyQuantity = 10;
        int sellQuantity = 5;
        long testPrice = 5000;

        Trade buyTrade = new Trade(account, stock, buyQuantity, TradeType.BUY);
        account.buy(buyTrade);

        Trade sellTrade = new Trade(account, stock, sellQuantity, TradeType.SELL);
        account.sell(sellTrade);

        assertEquals(500000 - testPrice * buyQuantity + testPrice * sellQuantity, account.getMoney());
        assertEquals(buyQuantity - sellQuantity, account.getTrades().get(0).getQuantity());
    }

    @Test
    void 매도_실패_테스트_불충분한_주식() {
        int buyQuantity = 10;
        int sellQuantity = 15;

        Trade buyTrade = new Trade(account, stock, buyQuantity, TradeType.BUY);
        account.buy(buyTrade);

        Trade sellTrade = new Trade(account, stock, sellQuantity, TradeType.SELL);

        assertThrows(IllegalArgumentException.class, () -> account.sell(sellTrade));
    }
}