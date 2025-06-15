package com.example.stocksimulation.domain.entity.cummunity;

import com.example.stocksimulation.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String contents;
    private LocalDateTime writeTime;

    @Builder
    public Comment(String contents, Member member, Board board) {
        this.board = board;
        this.contents = contents;
        this.member = member;
        this.writeTime = LocalDateTime.now();
    }

    public String getAuthorName() {
        return this.member.getNickName();
    }
}
