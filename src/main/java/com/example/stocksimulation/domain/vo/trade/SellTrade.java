package com.example.stocksimulation.domain.vo.trade;

import com.example.stocksimulation.domain.entity.Account;

public class SellTrade extends Trade {
    public SellTrade(String stockName, String stockCode, int quantity) {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.quantity = quantity;
    }

    @Override
    public void proceed(Account account, long price) {
        account.sellTrade(price, this);
    }
}
