package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.dto.trade.TradeTraceDto;
import com.example.stocksimulation.service.stock.TradeTraceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TradeTraceController {
    private final TradeTraceService service;

    @GetMapping("/trade-trace")
    public ResponseEntity<List<TradeTraceDto>> readAllTrace() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        List<TradeTraceDto> response = service.readAll(memberEmail);
        return ResponseEntity.ok(response);
    }
}
