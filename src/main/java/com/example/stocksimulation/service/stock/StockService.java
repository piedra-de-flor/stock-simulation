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
                    future.completeExceptionally(new Exception("Stock not found"));
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

    public List<Long> getStocksPrice(List<String> stockCodes) {
        List<Long> prices = new ArrayList<>();
        for (String stockCode : stockCodes) {
            prices.add(getStockPrice(stockCode));
        }

        return prices;
    }
}
