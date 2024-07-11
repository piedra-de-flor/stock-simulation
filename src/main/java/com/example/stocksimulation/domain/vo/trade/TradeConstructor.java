package com.example.stocksimulation.domain.vo.trade;

public enum TradeConstructor {
    BUY {
        @Override
        public Trade createTrade(String stockName, String stockCode, int quantity) {
            return BuyTrade.builder()
                    .stockName(stockName)
                    .stockCode(stockCode)
                    .quantity(quantity)
                    .build();
        }
    },
    SELL {
        @Override
        public Trade createTrade(String stockName, String stockCode, int quantity) {
            return SellTrade.builder()
                    .stockName(stockName)
                    .stockCode(stockCode)
                    .quantity(quantity)
                    .build();
        }
    };

    public abstract Trade createTrade(String stockName, String stockCode, int quantity);
}
