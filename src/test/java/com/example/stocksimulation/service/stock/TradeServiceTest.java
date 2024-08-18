package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.service.trade.TradeService;
import com.example.stocksimulation.service.trade.TradeTraceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Mock
    private AccountRepository accountRepository;

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
        //when(accountRepository.findByMember(member)).thenReturn(Optional.of(account));
        when(stock.getName()).thenReturn("Test Stock");
        when(stock.getPrice()).thenReturn(100L);

        boolean result = tradeService.trade(memberEmail, dto, tradeConstructor, tradeType);

        verify(traceService, times(1)).recordTrace(account, stock, dto.quantity(), tradeType);
        verify(memberRepository, times(1)).getMemberByEmail(memberEmail);
        verify(stockRepository, times(1)).findByCode(dto.stockCode());
    }

    @Test
    void testPessimisticLockDuringTrade() throws InterruptedException, ExecutionException {
        String memberEmail = "test@test.com";
        TradeRequestDto dto = new TradeRequestDto("005930", 10);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Boolean> future1 = executor.submit(() -> {
            System.out.println("Thread 1: Start transaction and lock the account");
            boolean result = tradeService.trade(memberEmail, dto, TradeConstructor.SELL, TradeType.SELL);
            System.out.println("Thread 1: Commit transaction and release the lock");
            return result;
        });

        Future<Boolean> future2 = executor.submit(() -> {
            System.out.println("Thread 2: Attempting to start transaction (should wait if lock is held)");
            boolean result = tradeService.trade(memberEmail, dto, TradeConstructor.SELL, TradeType.SELL);
            System.out.println("Thread 2: Transaction completed");
            return result;
        });


        Boolean result1 = future1.get();
        Boolean result2 = future2.get();

        executor.shutdown();

        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }
}
