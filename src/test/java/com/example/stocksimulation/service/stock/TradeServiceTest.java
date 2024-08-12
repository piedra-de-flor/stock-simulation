/*
package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.domain.vo.trade.Trade;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.service.trade.TradeService;
import com.example.stocksimulation.service.trade.TradeTraceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeTraceService traceService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    void 거래_성공_테스트() {
        String memberEmail = "test@example.com";
        TradeRequestDto dto = new TradeRequestDto("stockCode", 10);
        TradeConstructor tradeConstructor = TradeConstructor.SELL;
        TradeType tradeType = TradeType.SELL;

        Member member = mock(Member.class);
        Account account = mock(Account.class);
        Stock stock = mock(Stock.class);

        when(memberRepository.getMemberByEmail(anyString())).thenReturn(member);
        when(stockRepository.findByCode(anyString())).thenReturn(Optional.of(stock));
        when(member.getAccount()).thenReturn(account);
        when(stock.getName()).thenReturn("Test Stock");
        when(stock.getPrice()).thenReturn(100L);

        boolean result = tradeService.trade(memberEmail, dto, tradeConstructor, tradeType);

        verify(traceService, times(1)).recordTrace(account, stock, dto.quantity(), tradeType);
        verify(memberRepository, times(1)).getMemberByEmail(memberEmail);
        verify(stockRepository, times(1)).findByCode(dto.stockCode());
    }
}
*/
