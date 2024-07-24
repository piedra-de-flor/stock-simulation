package com.example.stocksimulation.web.controller.cummunity;

import com.example.stocksimulation.dto.cummunity.BoardCreateDto;
import com.example.stocksimulation.service.cummunity.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "게시물 생성", description = "새로운 게시물을 작성합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PostMapping("/board")
    public ResponseEntity<Void> create(
            @Parameter(description = "게시물 작성 요소들", required = true)
            @RequestBody BoardCreateDto createDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        boardService.create(createDto, memberEmail);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시물 삭제", description = "기존의 게시물을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
            @Parameter(description = "삭제할 게시물 id", required = true)
            @RequestBody Long boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        boardService.delete(memberEmail, boardId);
        return ResponseEntity.ok().build();
    }
}
