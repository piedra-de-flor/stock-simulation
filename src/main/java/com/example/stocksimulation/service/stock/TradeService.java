package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.domain.entity.Trade;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.TradeVO;
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
    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;

    @Transactional
    public boolean trade(String memberEmail, TradeRequestDto dto, TradeType tradeType) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Stock stock = stockRepository.findByCode(dto.stockCode())
                .orElseThrow(NoSuchElementException::new);
        Account account = member.getAccount();

        Trade trade;
        switch (tradeType) {
            case BUY :
                trade = Trade.builder()
                    .account(account)
                    .stock(stock)
                    .quantity(dto.quantity())
                    .tradeType(TradeType.BUY)
                    .build();

                account.buy(trade);
                tradeRepository.save(trade);
                break;
            case SELL :
                trade = Trade.builder()
                        .account(account)
                        .stock(stock)
                        .quantity(dto.quantity())
                        .tradeType(TradeType.BUY)
                        .build();

                account.sell(trade);
                deleteCompletedTradesAsync();
                tradeRepository.save(trade);
                break;
        }

        return true;
    }

    @Async
    public void deleteCompletedTradesAsync() {
        List<Trade> completedTrades = tradeRepository.findAllByQuantity(TradeVO.EMPTY_TRADE.getQuantity());
        tradeRepository.deleteAll(completedTrades);
    }
}
