package com.example.stocksimulation.domain.vo.trade;

public enum TradeConstructor {
    BUY {
        @Override
        public Trade createTrade(String stockName, String stockCode, int quantity) {
            return new BuyTrade(stockName, stockCode, quantity);
        }
    },
    SELL {
        @Override
        public Trade createTrade(String stockName, String stockCode, int quantity) {
            return new SellTrade(stockName, stockCode, quantity);
        }
    };

    public abstract Trade createTrade(String stockName, String stockCode, int quantity);
}
