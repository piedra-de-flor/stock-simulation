package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.TradeTrace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeTraceRepository extends JpaRepository<TradeTrace, Long> {
    List<TradeTrace> findAllByAccount(Account account);
}
