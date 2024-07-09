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
    public boolean buy(String memberEmail, TradeRequestDto dto) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Stock stock = stockRepository.findByCode(dto.stockCode())
                .orElseThrow(NoSuchElementException::new);
        Account account = member.getAccount();

        if (account.hasTrade(stock.getCode())) {
            account.buy(dto.quantity(), stock.getPrice());
            tradeRepository.findAllByAccount(account).stream()
                    .filter(oldTrade -> oldTrade.getStockCode().equals(stock.getCode()))
                    .findFirst()
                    .get()
                    .buy(dto.quantity());
        } else {
            Trade trade = Trade.builder()
                    .account(account)
                    .stock(stock)
                    .tradeType(TradeType.BUY)
                    .quantity(dto.quantity())
                    .build();

            account.buy(dto.quantity(), stock.getPrice());
            tradeRepository.save(trade);
            account.getTrades().add(trade);
        }

        traceService.recordTrace(account, stock, dto.quantity(), TradeType.BUY);
        return true;
    }

    @Transactional
    public boolean sell(String memberEmail, TradeRequestDto dto) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Stock stock = stockRepository.findByCode(dto.stockCode())
                .orElseThrow(NoSuchElementException::new);
        Account account = member.getAccount();

        if (account.canSell(stock.getCode(), dto.quantity())) {
            account.sell(dto.quantity(), stock.getPrice());
            Trade trade = tradeRepository.findAllByAccount(account).stream()
                    .filter(hasTrade -> hasTrade.getStockCode().equals(stock.getCode()))
                    .findFirst().get();

            trade.sell(dto.quantity());
            traceService.recordTrace(account, stock, dto.quantity(), TradeType.SELL);
        }

        return true;
    }

    // 배치로 돌려
    @Async
    public void deleteCompletedTradesAsync() {
        List<Trade> completedTrades = tradeRepository.findAllByQuantity(ZeroTradeQuantity.EMPTY_TRADE.getQuantity());
        tradeRepository.deleteAll(completedTrades);
    }
}
