package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.stock.Stock;
import com.example.stocksimulation.dto.stock.StockDto;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.service.account.WebSocketForBalance;
import com.example.stocksimulation.service.support.WebSocketClientToServerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository repository;
    private final WebSocketClientToServerHandler socketServerHandler;
    private final WebSocketForBalance socketForBalance;

    @Transactional
    public void updateStockPrice(String stockCode, int price) {
        Stock stock = repository.findByCode(stockCode)
                .orElseThrow(NoSuchElementException::new);
        if (stock.getPrice() != price) {
            stock.updatePrice(price);
            StockDto stockDto = new StockDto(stockCode, price, stock.getName());
            socketServerHandler.stocks.put(stockCode, stockDto);
            socketServerHandler.sendStockDtoToAllClients(stockDto);
            socketForBalance.sendMessageToAllClients();
        }
    }

    public List<StockDto> readAll() {
        List<StockDto> response = new ArrayList<>();
        repository.findAll()
                .forEach(stock -> response.add(new StockDto(stock.getCode(), stock.getPrice(), stock.getName())));
        return response;
    }

    public List<Long> getStockPriceByName(List<String> names) {
        List<Long> prices = new ArrayList<>();
        for (String name : names) {
            Stock stock = repository.findByName(name).get();
            prices.add(stock.getPrice());
        }

        return prices;
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
