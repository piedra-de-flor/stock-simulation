package com.example.stocksimulation.dto.membership;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberSignInDto(
        @NotBlank(message = "Email can not be null")
        @Email
        String email,
        @NotBlank(message = "Password can not be null")
        @Size(min = 4, max = 13, message = "password size must be between 4 and 13")
        String password
) {
}
