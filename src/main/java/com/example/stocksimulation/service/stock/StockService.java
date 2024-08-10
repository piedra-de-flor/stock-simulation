package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.service.account.WebSocketForBalance;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class StockService {
    private final WebSocketForBalance socketForBalance;
    private final DatabaseReference databaseReference;
    int count = 0;

    public Stock getStock(String stockCode) {
        Stock stock;
        CompletableFuture<Stock> future = new CompletableFuture<>();
        databaseReference.child(stockCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Stock stock = dataSnapshot.getValue(Stock.class);
                    future.complete(stock);
                } else {
                    Stock newStock = addStock(stockCode, "test-stock" + count++, 0L);
                    future.complete(newStock);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new Exception(databaseError.getMessage()));
            }
        });

        try {
            stock = future.get();
        } catch (Exception e) {
            throw new IllegalArgumentException("get stock data from firebase is fail");
        }

        return stock;
    }

    public void updateStockPrice(String stockCode, long newPrice) {
        if (getStockPrice(stockCode) != newPrice) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("price", newPrice);
            databaseReference.child(stockCode).updateChildrenAsync(updates);
            socketForBalance.sendMessageToAllClients();
        }
    }

    public long getStockPrice(String stockCode) {
        Stock stock = getStock(stockCode);
        return stock.getPrice();
    }

    private Stock addStock(String stockCode, String stockName, long price) {
        Stock newStock = new Stock(stockCode, price, stockName);
        databaseReference.child(stockCode).setValueAsync(newStock);
        return newStock;
    }

    public List<Long> getStocksPrice(List<String> stockCodes) {
        List<Long> prices = new ArrayList<>();
        for (String stockCode : stockCodes) {
            prices.add(getStockPrice(stockCode));
        }

        return prices;
    }
}
