package com.example.stocksimulation.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberTest {
    @Test
    void 회원_업데이트_테스트() {
        Member member = new Member();
        member.update("test", "test");

        assertThat(member.getNickName()).isEqualTo("test");
        assertThat(member.getPassword()).isEqualTo("test");
    }
}