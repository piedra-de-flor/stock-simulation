package com.example.stocksimulation.service.account;

import com.example.stocksimulation.domain.entity.stock.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.dto.account.AccountInfoDto;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public AccountInfoDto create(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        Account account = new Account(member);
        accountRepository.save(account);
        member.setAccount(account);
        return new AccountInfoDto(member.getNickName(), 0, null);
    }

    public AccountInfoDto readAccount(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        Account account = member.getAccount();

        return new AccountInfoDto(member.getNickName(), account.getMoney(), account.getHasTrades());
    }

    public Map<String, Integer> getTrades(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        Account account = member.getAccount();
        return account.getHasTrades();
    }

    public boolean deposit(long money, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        Account account = member.getAccount();
        account.deposit(money);
        return true;
    }

    public boolean withdraw(long money, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        Account account = member.getAccount();
        account.withdraw(money);
        return true;
    }
}
