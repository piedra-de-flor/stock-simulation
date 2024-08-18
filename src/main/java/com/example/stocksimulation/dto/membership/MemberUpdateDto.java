package com.example.stocksimulation.dto.membership;

public record MemberUpdateDto(
        long memberId,
        String nickName,
        String password
) {
}
