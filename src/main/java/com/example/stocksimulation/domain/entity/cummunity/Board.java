package com.example.stocksimulation.domain.entity.cummunity;

import com.example.stocksimulation.domain.entity.Member;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String contents;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments;
}
