package com.example.stocksimulation.domain.entity.cummunity;

import com.example.stocksimulation.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(String title, Member member, String contents) {
        this.title = title;
        this.member = member;
        this.contents = contents;
    }

    public String getAuthorName() {
        return this.member.getNickName();
    }
}
