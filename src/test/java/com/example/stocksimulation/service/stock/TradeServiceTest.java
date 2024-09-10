package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.stock.Account;
import com.example.stocksimulation.domain.entity.stock.Stock;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.service.trade.TradeService;
import com.example.stocksimulation.service.trade.TradeTraceService;
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
    private AccountRepository accountRepository;

    @Mock
    private StockService stockService;

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
        when(stockService.getStock(anyString())).thenReturn(stock);
        when(stock.getName()).thenReturn("Test Stock");
        when(stock.getPrice()).thenReturn(100L);
        when(accountRepository.findByMember(any())).thenReturn(Optional.of(account));

        tradeService.trade(memberEmail,dto, tradeConstructor, tradeType);

        verify(traceService, times(1)).recordTrace(account, stock, dto.quantity(), tradeType);
        verify(memberRepository, times(1)).getMemberByEmail(memberEmail);
        verify(stockService, times(1)).getStock(dto.stockCode());
    }
}
