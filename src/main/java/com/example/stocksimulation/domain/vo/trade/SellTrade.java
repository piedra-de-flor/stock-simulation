package com.example.stocksimulation.domain.vo.trade;

import com.example.stocksimulation.domain.entity.Account;

public class SellTrade extends Trade {
    @Override
    public void proceed(Account account, long price) {
        account.sellTrade(quantity, price, this);
    }
}
