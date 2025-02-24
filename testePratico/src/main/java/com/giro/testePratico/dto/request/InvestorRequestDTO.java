package com.giro.testePratico.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InvestorRequestDTO(
        @NotBlank String name,
        @NotBlank @Email String email
) {}
