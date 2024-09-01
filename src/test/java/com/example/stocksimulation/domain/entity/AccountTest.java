package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.domain.entity.stock.Account;
import com.example.stocksimulation.domain.vo.trade.Trade;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        Member member = new Member("test", "test@test.com", "testpw", new ArrayList<>());
        account = new Account(member);
    }

    //TODO
    // - 출금, 입금 기능이 완성된 후 테스트 가능
    @Test
    void 매수_성공_테스트() {
        int testPrice = 100;
        int testQuantity = 10;

        account.deposit(testPrice * (testQuantity + 1));
        Trade testTrade = TradeConstructor.BUY.createTrade("test", "testCode", testQuantity);

        account.buyTrade(testPrice, testTrade);

        assertEquals(testPrice, account.getMoney());
        assertEquals(1, account.getHasTrades().size());
    }

    @Test
    void 매도_성공_테스트() {
        int testPrice = 100;
        int buyQuantity = 10;
        int sellQuantity = 5;

        account.deposit(testPrice * buyQuantity);

        Trade buyTrade = TradeConstructor.BUY.createTrade("test", "testCode", buyQuantity);
        Trade sellTrade = TradeConstructor.SELL.createTrade("test", "testCode", sellQuantity);

        account.buyTrade(testPrice, buyTrade);
        account.sellTrade(testPrice, sellTrade);

        long expectMoney = (testPrice * buyQuantity) - (testPrice * sellQuantity);
        int expectSize = 5;

        assertEquals(expectMoney, account.getMoney());
        assertEquals(expectSize, account.getHasTrades().size());
    }

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
