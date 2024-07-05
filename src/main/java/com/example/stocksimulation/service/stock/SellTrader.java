package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Trade;
import com.example.stocksimulation.repository.TradeRepository;

public class SellTrader extends Trader {
    public SellTrader(TradeRepository tradeRepository) {
        super(tradeRepository);
    }

    @Override
    public void trade(Account account, Trade trade) {
        if (account.canSell(trade.getStockCode(), trade.getQuantity())) {
            account.sell(trade.getQuantity(), trade.getStockPrice());
            trade.sell(trade.getQuantity());
        }
    }
}
