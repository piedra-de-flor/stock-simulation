package com.example.stocksimulation.web.controller.account;

import com.example.stocksimulation.dto.account.AccountInfoDto;
import com.example.stocksimulation.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService service;

    @PostMapping("/account")
    public ResponseEntity<AccountInfoDto> makeAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        AccountInfoDto response = service.create(memberEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account")
    public ResponseEntity<AccountInfoDto> read() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        AccountInfoDto response = service.readAccount(memberEmail);
        return ResponseEntity.ok(response);
    }

    @PostMapping("account/deposit")
    public ResponseEntity<Boolean> deposit(long money) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        boolean response = service.deposit(money, memberEmail);
        return ResponseEntity.ok(response);
    }

    @PostMapping("account/withdraw")
    public ResponseEntity<Boolean> withdraw(long money) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        boolean response = service.withdraw(money, memberEmail);
        return ResponseEntity.ok(response);
    }
}
