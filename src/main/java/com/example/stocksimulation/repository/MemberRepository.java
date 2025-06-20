package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Member getMemberByEmail(String email);
}
