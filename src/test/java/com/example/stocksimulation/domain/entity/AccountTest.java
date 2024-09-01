package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.domain.entity.stock.Account;
import com.example.stocksimulation.domain.vo.trade.Trade;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    //TODO
    // - 출금, 입금 기능이 완성된 후 테스트 가능
    /*@Test
    void 매수_성공_테스트() {
        int testQuantity = 10;
        Trade testTrade = TradeConstructor.BUY.createTrade("test", "testCode", testQuantity);

        account.buyTrade(stock.getPrice(), testTrade);

        assertEquals(-stock.getPrice() * testQuantity, account.getMoney());
        assertEquals(1, account.getHasTrades().size());
    }

    @Test
    void 매도_성공_테스트() {
        int buyQuantity = 10;
        int sellQuantity = 5;

        account.sell("test", sellQuantity, stock.getPrice());

        assertEquals(-stock.getPrice() * buyQuantity + stock.getPrice() * sellQuantity, account.getMoney());
        assertEquals(buyQuantity - sellQuantity, account.getHasTrades().get(0).getQuantity());
    }*/

    @Test
    void 매수_실패_테스트_불충분한_돈() {
        Trade testTrade = TradeConstructor.BUY.createTrade("test", "testCode", 10);

        Assertions.assertThrows(IllegalArgumentException.class, () -> account.buyTrade(5000, testTrade));
    }

    @Test
    void 매도_실패_테스트_불충분한_주식() {
        Trade testTrade = TradeConstructor.SELL.createTrade("test", "testCode", 10);

        Assertions.assertThrows(IllegalArgumentException.class, () -> account.sellTrade(5000, testTrade));
    }
}
