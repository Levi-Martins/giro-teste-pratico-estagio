package com.giro.testePratico.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExchangeRateRequestDTO(
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull
        float dailyVariation,

        @NotNull
        float dailyRate,

        @NotNull
        Long currencyId
) {}
