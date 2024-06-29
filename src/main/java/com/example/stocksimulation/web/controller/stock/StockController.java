package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.domain.vo.WebSocketClientVO;
import com.example.stocksimulation.service.stock.StockService;
import com.example.stocksimulation.service.stock.WebSocketConnectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class StockController {
    private final WebSocketConnectService service;
    private final StockService stockService;

    @GetMapping("/stocks")
    public void stocks() {
        service.connect();
    }

    @GetMapping("/send")
    public void send() {
        String request = WebSocketClientVO.WEB_SOCKET_CLIENT_REQUEST.getValue();
        service.send(request);
    }

    @GetMapping("/stock-test/{price}")
    public String test(@PathVariable int price) {
        stockService.saveTest();
        stockService.updateStockPrice("005930", price);
        return "execute";
    }
}
