package com.example.stocksimulation.domain.entity.cummunity;

import com.example.stocksimulation.domain.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String contents;
    private LocalDateTime writeTime;
}
