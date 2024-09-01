package com.example.stocksimulation.service.trade;

import com.example.stocksimulation.domain.entity.stock.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.stock.Stock;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.trade.Trade;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TradeService {
    private final TradeTraceService traceService;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final StockService stockService;

    @Transactional
    public boolean trade(String memberEmail, TradeRequestDto dto, TradeConstructor tradeConstructor, TradeType tradeType) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Stock stock = stockService.getStock(dto.stockCode());
        Account account = accountRepository.findByMember(member)
                .orElseThrow(IllegalArgumentException::new);

        Trade trade = tradeConstructor.createTrade(stock.getName(), dto.stockCode(), dto.quantity());
        trade.proceed(account, stock.getPrice());
        traceService.recordTrace(account, stock, dto.quantity(), tradeType);
        return true;
    }
}
