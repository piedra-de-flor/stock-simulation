package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository repository;

    public void updateStockPrice(String stockCode, String price) {
        Stock stock = repository.findByCode(stockCode)
                .orElseThrow(NoSuchElementException::new);

        stock.updatePrice(Integer.parseInt(price));
    }
}
