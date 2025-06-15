package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.dto.stock.StockHistoryResponseDto;
import com.example.stocksimulation.service.stock.StockHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockHistoryController {
    private final StockHistoryService service;

    @GetMapping("/history/{stockCode}")
    public ResponseEntity<List<StockHistoryResponseDto>> readStockHistory(@PathVariable("stockCode") String stockCode) {
        List<StockHistoryResponseDto> response = service.getStockHistory(stockCode);
        return ResponseEntity.ok(response);
    }
}
