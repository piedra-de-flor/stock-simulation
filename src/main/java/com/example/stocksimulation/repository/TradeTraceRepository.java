package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.stock.Account;
import com.example.stocksimulation.domain.entity.stock.TradeTrace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeTraceRepository extends JpaRepository<TradeTrace, Long> {
    List<TradeTrace> findAllByAccount(Account account);
}
