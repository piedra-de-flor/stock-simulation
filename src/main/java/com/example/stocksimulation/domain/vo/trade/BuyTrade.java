package com.example.stocksimulation.domain.vo.trade;

import com.example.stocksimulation.domain.entity.stock.Account;

public class BuyTrade extends Trade {
    public BuyTrade(String stockName, String stockCode, int quantity) {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.quantity = quantity;
    }

    @Override
    public void proceed(Account account, long price) {
        account.buyTrade(price, this);
    }
}
