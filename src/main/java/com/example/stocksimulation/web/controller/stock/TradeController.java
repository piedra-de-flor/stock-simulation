package com.example.stocksimulation.web.controller.stock;

import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.service.stock.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TradeController {
    private final TradeService service;

    @PostMapping("/buy")
    public ResponseEntity<Boolean> buy(@RequestBody TradeRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자의 이름을 가져옴
        String memberId = authentication.getName();
        boolean response = service.trade(memberId, dto, TradeType.BUY);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sell")
    public ResponseEntity<Boolean> sell(@RequestBody TradeRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자의 이름을 가져옴
        String memberId = authentication.getName();
        boolean response = service.trade(memberId, dto, TradeType.SELL);
        return ResponseEntity.ok(response);
    }
}
