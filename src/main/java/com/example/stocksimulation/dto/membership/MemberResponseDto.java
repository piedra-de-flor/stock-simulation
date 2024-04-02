package com.example.stocksimulation.dto.membership;

import com.example.stocksimulation.domain.entity.Member;

public record MemberResponseDto(long userId, String name, String email, String role) {
    public static MemberResponseDto fromUser(Member member) {
        return new MemberResponseDto(member.getId(), member.getNickName(), member.getEmail(), member.getRoles().get(0));
    }
}
