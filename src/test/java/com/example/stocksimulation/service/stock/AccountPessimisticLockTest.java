package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.domain.vo.trade.TradeConstructor;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.service.trade.TradeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.CannotAcquireLockException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class AccountPessimisticLockTest {
    @Autowired
    private TradeService tradeService;

    @Test
    public void testPessimisticLocking() throws InterruptedException {
        int threadCount = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        TradeRequestDto dto = new TradeRequestDto("005930", 1);

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
    }
}