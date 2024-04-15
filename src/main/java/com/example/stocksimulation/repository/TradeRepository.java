package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
