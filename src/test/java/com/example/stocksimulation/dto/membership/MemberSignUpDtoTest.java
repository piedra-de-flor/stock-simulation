package com.example.stocksimulation.dto.membership;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MemberSignUpDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void 회원가입_DTO_유효성_성공() {
        MemberSignUpDto dto = new MemberSignUpDto("test", "test@test.com", "password123");
        Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void 회원가입_DTO_유효성_실패_이름_null() {
        MemberSignUpDto dto = new MemberSignUpDto(null, "test@test.com", "password123");
        Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name can not be null");
    }

    @Test
    void 회원가입_DTO_유효성_실패_이메일_null() {
        MemberSignUpDto dto = new MemberSignUpDto("test", null, "password123");
        Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email can not be null");
    }

    @Test
    void 회원가입_DTO_유효성_실패_이메일_형식_X() {
        MemberSignUpDto dto = new MemberSignUpDto("test", "test", "password123");
        Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("올바른 형식의 이메일 주소여야 합니다");
    }

    @Test
    void 회원가입_DTO_유효성_실패_비밀번호_null() {
        MemberSignUpDto dto = new MemberSignUpDto("test", "test@test.com", null);
        Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password can not be null");
    }

    @Test
    void 회원가입_DTO_유효성_실패_비밀번호_길이제한_미달() {
        MemberSignUpDto dto = new MemberSignUpDto("test", "test@test.com", "pas");
        Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("password size must be between 4 and 13");
    }

    @Test
    void 회원가입_DTO_유효성_실패_비밀번호_길이제한_초과() {
        MemberSignUpDto dto = new MemberSignUpDto("test", "test@test.com", "password12345678910");
        Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("password size must be between 4 and 13");
    }
}