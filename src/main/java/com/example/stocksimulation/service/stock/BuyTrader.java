package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Trade;
import com.example.stocksimulation.repository.TradeRepository;

public class BuyTrader extends Trader {
    public BuyTrader(TradeRepository tradeRepository) {
        super(tradeRepository);
    }

    @Override
    public void trade(Account account, Trade trade) {
        if (account.hasTrade(trade.getStockCode())) {
            account.buy(trade.getQuantity(), trade.getStockPrice());
            tradeRepository.findAllByAccount(account).stream()
                    .filter(oldTrade -> oldTrade.getStockCode().equals(trade.getStockCode()))
                    .findFirst()
                    .get()
                    .buy(trade.getQuantity());
        } else {
            account.buy(trade.getQuantity(), trade.getStockPrice());
            tradeRepository.save(trade);
            account.getTrades().add(trade);
        }

    }
}
