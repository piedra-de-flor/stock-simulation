package com.example.stocksimulation.service.account;

import com.example.stocksimulation.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BalanceService {
    private final AccountService accountService;
    private final StockService stockService;

    public long getBalance(String email) {
        long balance = 0;
        Map<String, Integer> trades = accountService.getTrades(email);
        List<Long> prices = stockService.getStocksPrice(trades.keySet().stream().toList());

        int index = 0;
        for (int quantity : trades.values()) {
            balance += prices.get(index) * quantity;
        }

        return balance;
    }
}
