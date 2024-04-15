package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.Stock;
import com.example.stocksimulation.domain.entity.Trade;
import com.example.stocksimulation.dto.trade.TradeRequestDto;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.StockRepository;
import com.example.stocksimulation.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TradeService {
    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;

    @Transactional
    public boolean buy(String memberEmail, TradeRequestDto dto) throws AuthenticationException {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(NoSuchElementException::new);

        if (member.getAccounts().contains(account)) {
            Stock stock = stockRepository.findByCode(dto.stockCode())
                    .orElseThrow(NoSuchElementException::new);
            long needMoney = stock.getPrice() * dto.quantity();

            if (account.getMoney() >= needMoney) {
                Trade trade = Trade.builder()
                        .account(account)
                        .stock(stock)
                        .quantity(dto.quantity())
                        .build();

                account.buy(needMoney);
                account.getTrades().add(trade);
                tradeRepository.save(trade);
                return true;
            } else {
                throw new IllegalArgumentException("not enough money");
            }
        } else {
            throw new AuthenticationException();
        }
    }

    public boolean sell(String memberEmail, TradeRequestDto dto) throws AuthenticationException {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(NoSuchElementException::new);

        if (member.getAccounts().contains(account)) {
            List<Trade> trades = account.getTrades().stream()
                    .filter(trade -> trade.getStock().getCode().equals(dto.stockCode()))
                    .sorted(Comparator.comparingInt(Trade::getQuantity).reversed())
                    .toList();

            int remainingQuantity = dto.quantity();

            for (Trade trade : trades) {
                if (remainingQuantity <= 0) {
                    break;
                }

                int tradeQuantity = trade.getQuantity();
                if (tradeQuantity >= remainingQuantity) {
                    trade.sell(remainingQuantity);
                    remainingQuantity = 0;
                } else {
                    tradeRepository.delete(trade);
                    remainingQuantity -= tradeQuantity;
                }
            }
            return true;
        } else {
            throw new AuthenticationException();
        }
    }
}
