package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StockController {
    private final StockService service;

    @GetMapping("/stock")
    public void stock() {
        service.connectAndSendRequest();
    }
}
