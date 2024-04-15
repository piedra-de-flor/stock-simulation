package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.service.stock.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TradeController {
    private final TradeService service;

    @PostMapping("/buy")
    public ResponseEntity<Boolean> buy(@RequestBody TradeRequestDto dto,
                                       HttpServletRequest request) {
        boolean response = service.trade((String) request.getAttribute("memberId"), dto, TradeType.BUY);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sell")
    public ResponseEntity<Boolean> sell(@RequestBody TradeRequestDto dto,
                                       HttpServletRequest request) {
        boolean response = service.trade((String) request.getAttribute("memberId"), dto, TradeType.SELL);
        return ResponseEntity.ok(response);
    }
}
