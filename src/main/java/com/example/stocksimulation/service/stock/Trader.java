package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Trade;
import com.example.stocksimulation.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public abstract class Trader {
    final TradeRepository tradeRepository;

    @Transactional
    public abstract void trade(Account account, Trade trade);
}
