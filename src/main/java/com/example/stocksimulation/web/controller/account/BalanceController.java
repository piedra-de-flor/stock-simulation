package com.example.stocksimulation.web.controller.account;

import com.example.stocksimulation.service.account.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BalanceController {
    private final BalanceService service;

    @GetMapping("/balance")
    public ResponseEntity<Long> getBalance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        long response = service.getBalance(memberEmail);
        return ResponseEntity.ok(response);
    }
}
