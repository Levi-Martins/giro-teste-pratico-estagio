package com.giro.testePratico.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CurrencyRequestDTO(@NotBlank String name,
                                 @NotBlank String type
) {}
