package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.domain.entity.Trade;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeTraceService traceService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private TradeService tradeService;

    private Member member;
    private Account account;
    private Stock stock;

    @BeforeEach
    void setUp() {
        member = mock(Member.class);
        account = new Account(member);
        stock = new Stock("TEST_CODE", 5000, "test");

        when(member.getAccount()).thenReturn(account);
    }

    @Test
    void 매수_성공_테스트() {
        TradeRequestDto dto = new TradeRequestDto("TEST_CODE", 10);
        when(memberRepository.getMemberByEmail(anyString())).thenReturn(member);
        when(stockRepository.findByCode(anyString())).thenReturn(Optional.of(stock));

        boolean result = tradeService.buy("test@test.com", dto);

        ArgumentCaptor<Trade> tradeCaptor = ArgumentCaptor.forClass(Trade.class);
        verify(tradeRepository).save(tradeCaptor.capture());
        Trade savedTrade = tradeCaptor.getValue();

        assertEquals(true, result);
        assertEquals(TradeType.BUY, savedTrade.getTradeType());
        assertEquals(10, savedTrade.getQuantity());
        assertEquals(stock, savedTrade.getStock());
        assertEquals(account, savedTrade.getAccount());
        verify(traceService).recordTrace(any(), any(), any(), any());
    }

    @Test
    void 매도_성공_테스트() {
        TradeRequestDto dto = new TradeRequestDto("TEST_CODE", 5);
        when(memberRepository.getMemberByEmail(anyString())).thenReturn(member);
        when(stockRepository.findByCode(anyString())).thenReturn(Optional.of(stock));

        account.buy(10, 1000);

        boolean result = tradeService.sell("test@test.com", dto);

        ArgumentCaptor<Trade> tradeCaptor = ArgumentCaptor.forClass(Trade.class);
        verify(tradeRepository).save(tradeCaptor.capture());
        Trade savedTrade = tradeCaptor.getValue();

        assertEquals(true, result);
        assertEquals(TradeType.BUY, savedTrade.getTradeType());
        assertEquals(5, savedTrade.getQuantity());
        assertEquals(stock, savedTrade.getStock());
        assertEquals(account, savedTrade.getAccount());
        verify(traceService).recordTrace(any(), any(), any(), any());
        verify(tradeRepository).deleteAll(anyList());
    }
}
