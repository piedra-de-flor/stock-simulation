package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.stock.Account;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.TradeTraceRepository;
import com.example.stocksimulation.service.trade.TradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.CannotAcquireLockException;

import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountPessimisticLockTest {
    private final String testCode = "086790";
    private final long testStockPrice = 60300;
    private final long testAccountMoney = 1000000;
    private final int threadCount = 10;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TradeTraceRepository tradeTraceRepository;

    private Account account;

    @Test
    public void testPessimisticLocking() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        TradeRequestDto dto = new TradeRequestDto(testCode, 1);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println("Thread " + Thread.currentThread().getId() + " is attempting to acquire lock.");
                    tradeService.trade("test@test.com", dto, TradeConstructor.BUY, TradeType.BUY);
                    System.out.println("Thread " + Thread.currentThread().getId() + " successfully acquired lock and completed the trade.");
                } catch (CannotAcquireLockException e) {
                    System.out.println("Thread " + Thread.currentThread().getId() + " could not acquire lock: " + e.getMessage());
                } catch (NoSuchElementException e) {
                    System.out.println("Thread " + Thread.currentThread().getId() + " NoSuch " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Thread " + Thread.currentThread().getId() + " encountered an unexpected error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        account = accountRepository.findById(1L).orElseThrow();
        long actual = account.getMoney();

        assertEquals(testAccountMoney - threadCount * testStockPrice, actual);
        assertEquals(1, account.getHasTrades().size());
    }

    @AfterEach
    public void cleanUp() {
        tradeTraceRepository.deleteAll();
        memberRepository.deleteAll();
        accountRepository.deleteAll();
    }
}