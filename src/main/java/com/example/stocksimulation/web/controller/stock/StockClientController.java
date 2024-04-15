package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.dto.stock.StockDto;
import com.example.stocksimulation.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StockClientController {
    private final StockService service;

    @GetMapping("/stocks-info")
    public ResponseEntity<List<StockDto>> getStocksInfo() {
        List<StockDto> response = service.readAll();
        return ResponseEntity.ok(response);
    }
}
