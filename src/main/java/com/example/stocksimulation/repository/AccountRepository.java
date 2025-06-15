package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.stock.Account;
import com.example.stocksimulation.domain.entity.Member;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.member = :member")
    Optional<Account> findByMember(Member member);
}
