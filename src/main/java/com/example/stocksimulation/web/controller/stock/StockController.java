package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.domain.vo.WebSocketParsingInfo;
import com.example.stocksimulation.service.stock.StockService;
import com.example.stocksimulation.service.stock.WebSocketConnectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

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
        String request = WebSocketParsingInfo.WEB_SOCKET_CLIENT_REQUEST.getValue();
        service.send(request);
    }
}
