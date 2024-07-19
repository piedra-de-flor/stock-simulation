package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.stock.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
