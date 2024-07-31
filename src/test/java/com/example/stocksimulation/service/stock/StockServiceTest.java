package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.dto.stock.StockDto;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.service.support.WebSocketClientToServerHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    @Mock
    private StockRepository repository;
    @Mock
    private WebSocketClientToServerHandler socketServerHandler;
    @InjectMocks
    private StockService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(socketServerHandler, "stocks", new HashMap<>());
    }

    @Test
    public void 주식_가격_업데이트_테스트() {
        String stockCode = "AAPL";
        int currentPrice = 100;
        int newPrice = 150;

        Stock stock = Stock.builder()
                .code(stockCode)
                .price(currentPrice)
                .name("test")
                .build();

        StockDto stockDto = new StockDto(stockCode, newPrice, "test");

        when(repository.findByCode(stockCode)).thenReturn(Optional.of(stock));

        service.updateStockPrice(stockCode, newPrice);

        verify(repository, times(1)).findByCode(stockCode);
        verify(socketServerHandler, times(1)).sendStockDtoToAllClients(stockDto);
        assertEquals(newPrice, stock.getPrice());
    }

    @Test
    public void 주식_가격_업데이트_실패_주식정보_없음_테스트() {
        String stockCode = "AAPL";
        int newPrice = 150;

        when(repository.findByCode(stockCode)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> service.updateStockPrice(stockCode, newPrice));
    }
}
