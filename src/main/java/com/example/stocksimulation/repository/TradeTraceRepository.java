package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.TradeTrace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeTraceRepository extends JpaRepository<TradeTrace, Long> {
}
