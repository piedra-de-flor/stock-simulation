package com.example.stocksimulation.service.cummunity;

import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.cummunity.Board;
import com.example.stocksimulation.domain.entity.cummunity.Comment;
import com.example.stocksimulation.dto.cummunity.CommentCreateDto;
import com.example.stocksimulation.repository.BoardRepository;
import com.example.stocksimulation.repository.CommentRepository;
import com.example.stocksimulation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public void create(CommentCreateDto createDto, String memberEmail) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Board board = boardRepository.getReferenceById(createDto.boardId());

        Comment newComment = Comment.builder()
                .board(board)
                .contents(createDto.contents())
                .member(member)
                .build();

        commentRepository.save(newComment);
    }

    public void delete(String memberEmail, long commentId) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Comment target = commentRepository.findById(commentId)
                .orElseThrow(IllegalArgumentException::new);

        if (isMine(target, member)) {
            commentRepository.delete(target);
        }
    }

    private boolean isMine(Comment comment, Member member) {
        if (comment.getAuthorName().equals(member.getNickName())) {
            return true;
        } else {
            throw new IllegalArgumentException("You don't have auth for this board");
        }
    }
}
