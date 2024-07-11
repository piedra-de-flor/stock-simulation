package com.example.stocksimulation.domain.vo.trade;

public enum TradeConstructor {
    BUY {
        @Override
        public Trade createTrader(String stockCode, int quantity) {
            return BuyTrade.builder()
                    .stockCode(stockCode)
                    .quantity(quantity)
                    .build();
        }
    },
    SELL {
        @Override
        public Trade createTrader(String stockCode, int quantity) {
            return SellTrade.builder()
                    .stockCode(stockCode)
                    .quantity(quantity)
                    .build();
        }
    };

    public abstract Trade createTrader(String stockCode, int quantity);
}
