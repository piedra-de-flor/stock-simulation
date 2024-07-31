package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.Account;
import com.example.stocksimulation.domain.entity.Member;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByMember(Member member);
}
