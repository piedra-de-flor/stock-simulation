package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.stock.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, String> {
}
