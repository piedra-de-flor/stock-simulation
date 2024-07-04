package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.dto.stock.StockDto;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.service.support.WebSocketClientToServerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository repository;
    private final WebSocketClientToServerHandler socketServerHandler;

    public void updateStockPrice(String stockCode, int price) {
        Stock stock = repository.findByCode(stockCode)
                .orElseThrow(NoSuchElementException::new);
        if (stock.getPrice() != price) {
            stock.updatePrice(price);
            StockDto stockDto = new StockDto(stockCode, price, stock.getName());
            socketServerHandler.stocks.put(stockCode, stockDto);
            socketServerHandler.sendStockDtoToAllClients(stockDto);
        }
    }

    public void saveTest() {
        Stock stock = Stock.builder()
                .code("005930")
                .name("test")
                .price(10000)
                .build();

        repository.save(stock);
    }
}
