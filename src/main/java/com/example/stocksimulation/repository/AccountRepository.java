package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
