package com.example.stocksimulation.repository;

import com.example.stocksimulation.domain.entity.cummunity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
