package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.*;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.ZeroTradeQuantity;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TradeService {
    private final TradeTraceService traceService;
    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;

    @Transactional
    public boolean trade(String memberEmail, TradeRequestDto dto, TraderConstructor traderConstructor, TradeType tradeType) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Stock stock = stockRepository.findByCode(dto.stockCode())
                .orElseThrow(NoSuchElementException::new);
        Account account = member.getAccount();

        Trade trade = Trade.builder()
                .account(account)
                .stock(stock)
                .quantity(dto.quantity())
                .tradeType(tradeType)
                .build();

        Trader trader = traderConstructor.createTrader(tradeRepository);

        trader.trade(account, trade);
        traceService.recordTrace(trade);

        return true;
    }

    // 배치로 돌려
    @Async
    public void deleteCompletedTradesAsync() {
        List<Trade> completedTrades = tradeRepository.findAllByQuantity(ZeroTradeQuantity.EMPTY_TRADE.getQuantity());
        tradeRepository.deleteAll(completedTrades);
    }
}
