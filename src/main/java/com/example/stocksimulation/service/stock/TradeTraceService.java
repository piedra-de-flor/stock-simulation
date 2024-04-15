package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Trade;
import com.example.stocksimulation.domain.entity.TradeTrace;
import com.example.stocksimulation.repository.TradeTraceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TradeTraceService {
    private final TradeTraceRepository repository;

    public void recordTrace(Trade trade) {
        TradeTrace trace = TradeTrace.builder()
                .trade(trade)
                .build();

        repository.save(trace);
    }
}
