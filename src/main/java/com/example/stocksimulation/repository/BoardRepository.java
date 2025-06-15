package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.cummunity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
