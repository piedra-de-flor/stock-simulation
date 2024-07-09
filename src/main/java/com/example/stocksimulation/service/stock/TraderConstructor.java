package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.*;
import com.example.stocksimulation.repository.TradeRepository;

public enum TraderConstructor {
    BUY {
        @Override
        public Trader createTrader(TradeRepository tradeRepository) {
            return new BuyTrader(tradeRepository);
        }
    },
    SELL {
        @Override
        public Trader createTrader(TradeRepository tradeRepository) {
            return new SellTrader(tradeRepository);
        }
    };

    public abstract Trader createTrader(TradeRepository tradeRepository);
}
