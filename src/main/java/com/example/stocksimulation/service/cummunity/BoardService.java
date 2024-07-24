package com.example.stocksimulation.service.cummunity;

import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.cummunity.Board;
import com.example.stocksimulation.dto.cummunity.BoardCreateDto;
import com.example.stocksimulation.repository.BoardRepository;
import com.example.stocksimulation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void create(BoardCreateDto createDto, String memberEmail) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Board newBoard = Board.builder()
                .title(createDto.title())
                .member(member)
                .contents(createDto.contents())
                .build();

        boardRepository.save(newBoard);
    }

    public void delete(String memberEmail, long boardId) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        Board target = boardRepository.findById(boardId)
                .orElseThrow(IllegalArgumentException::new);

        if (isMine(target, member)) {
            boardRepository.delete(target);
        }
    }

    private boolean isMine(Board board, Member member) {
        if (board.getAuthorName().equals(member.getNickName())) {
            return true;
        } else {
            throw new IllegalArgumentException("You don't have auth for this board");
        }
    }
}
