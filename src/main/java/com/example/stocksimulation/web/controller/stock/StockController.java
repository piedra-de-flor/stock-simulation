package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
public class StockController {
    private final StockService service;

    @GetMapping("/stocks")
    public void stocks() throws IOException, ExecutionException, InterruptedException {
        service.test();
    }

    @GetMapping("/send")
    public void send() throws IOException {
        String request = "{\"header\":{\"approval_key\":\"1d2d8d5f-b6de-46e5-be74-96312a693a76\",\"custtype\":\"P\",\"tr_type\":\"1\",\"content-type\":\"utf-8\"},\"body\":{\"input\":{\"tr_id\":\"H0STCNT0\",\"tr_key\":\"005930\"}}}";
        service.send(request);
    }
}
