package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.domain.vo.WebSocketClientVO;
import com.example.stocksimulation.service.stock.WebSocketConnectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StockController {
    private final WebSocketConnectService service;

    @GetMapping("/stocks")
    public void stocks() {
        service.connect();
    }

    @GetMapping("/send")
    public void send() {
        String request = WebSocketClientVO.WEB_SOCKET_CLIENT_REQUEST.getValue();
        service.send(request);
    }
}
