package com.example.stocksimulation.web.controller.membership;

import com.example.stocksimulation.dto.membership.JwtToken;
import com.example.stocksimulation.dto.membership.MemberResponseDto;
import com.example.stocksimulation.dto.membership.MemberSignInDto;
import com.example.stocksimulation.dto.membership.MemberSignUpDto;
import com.example.stocksimulation.dto.membership.MemberUpdateDto;
import com.example.stocksimulation.service.membership.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원 Controller", description = "Member API")
@RestController
public class MemberController {
    private final MemberService service;

    @Operation(summary = "회원가입", description = "새로운 회원을 생성합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponseDto> signUp(
            @Parameter(description = "회원 가입 요청 정보", required = true)
            @RequestBody @Validated final MemberSignUpDto userJoinRequestDto) {
        MemberResponseDto responseDto = service.signUp(userJoinRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "로그인", description = "이메일와 비밀번호를 통해 로그인합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(
            @Parameter(description = "로그인 요청 정보", required = true)
            @RequestBody @Validated MemberSignInDto signInDto) {
        JwtToken token = service.signIn(signInDto.email(), signInDto.password());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PatchMapping("/member")
    public ResponseEntity<MemberUpdateDto> update(
            @Parameter(description = "수정 요소들", required = true)
            @RequestBody MemberUpdateDto updateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        MemberUpdateDto response = service.update(updateDto, memberEmail);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 정보 삭제", description = "회원 정보를 삭제합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @DeleteMapping("/member")
    public ResponseEntity<String> delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        String response = service.delete(memberEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/jenkins-test")
    public ResponseEntity<String> jenkins() {
        return ResponseEntity.ok("jenkins ci/cd number 1");
    }
}
