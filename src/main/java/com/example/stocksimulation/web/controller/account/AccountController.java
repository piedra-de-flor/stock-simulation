package com.example.stocksimulation.web.controller.account;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.repository.AccountRepository;
import com.example.stocksimulation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountRepository repository;
    private final MemberRepository memberRepository;

    @PostMapping("/account")
    public void makeAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        Member m = memberRepository.findByEmail(memberId)
                .orElseThrow(NoSuchElementException::new);

        Account account = new Account(m);
        m.setAccount(account);
        repository.save(account);
    }
}
