package com.example.stocksimulation.dto.membership;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MemberSignInDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void 로그인_DTO_유효성_성공() {
        MemberSignInDto dto = new MemberSignInDto("test@test.com", "password123");
        Set<ConstraintViolation<MemberSignInDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void 로그인_DTO_유효성_실패_이메일_null() {
        MemberSignInDto dto = new MemberSignInDto( null, "password123");
        Set<ConstraintViolation<MemberSignInDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email can not be null");
    }

    @Test
    void 로그인_DTO_유효성_실패_이메일_형식_X() {
        MemberSignInDto dto = new MemberSignInDto("test", "password123");
        Set<ConstraintViolation<MemberSignInDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("올바른 형식의 이메일 주소여야 합니다");
    }

    @Test
    void 로그인_DTO_유효성_실패_비밀번호_null() {
        MemberSignInDto dto = new MemberSignInDto("test@test.com", null);
        Set<ConstraintViolation<MemberSignInDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password can not be null");
    }

    @Test
    void 로그인_DTO_유효성_실패_비밀번호_길이제한_미달() {
        MemberSignInDto dto = new MemberSignInDto( "test@test.com", "pas");
        Set<ConstraintViolation<MemberSignInDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("password size must be between 4 and 13");
    }

    @Test
    void 로그인_DTO_유효성_실패_비밀번호_길이제한_초과() {
        MemberSignInDto dto = new MemberSignInDto("test@test.com", "password12345678910");
        Set<ConstraintViolation<MemberSignInDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("password size must be between 4 and 13");
    }
}