package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.dto.stock.StockDto;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.service.support.WebSocketClientToServerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository repository;
    private final WebSocketClientToServerHandler socketServerHandler;

    @Transactional
    public void updateStockPrice(String stockCode, int price) {
        Stock stock = repository.findByCode(stockCode)
                .orElseThrow(NoSuchElementException::new);
        if (stock.getPrice() != price) {
            stock.updatePrice(price);
            StockDto stockDto = stock.toDto();
            socketServerHandler.stocks.put(stockCode, stockDto);
            socketServerHandler.sendStockDtoToAllClients(stockDto);
        }
    }

    public List<StockDto> readAll() {
        return repository.findAll().stream()
                .map(Stock::toDto)
                .collect(Collectors.toList());
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
