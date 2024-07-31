package com.example.stocksimulation.service.trade;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.domain.vo.trade.Trade;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TradeService {
    private final TradeTraceService traceService;
    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public boolean trade(String memberEmail, TradeRequestDto dto, TradeConstructor tradeConstructor, TradeType tradeType) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Stock stock = stockRepository.findByCode(dto.stockCode())
                .orElseThrow(NoSuchElementException::new);
        Account account = accountRepository.findByMember(member)
                .orElseThrow(IllegalArgumentException::new);

        Trade trade = tradeConstructor.createTrade(stock.getName(), dto.stockCode(), dto.quantity());
        trade.proceed(account, stock.getPrice());
        traceService.recordTrace(account, stock, dto.quantity(), tradeType);
        return true;
    }
}
