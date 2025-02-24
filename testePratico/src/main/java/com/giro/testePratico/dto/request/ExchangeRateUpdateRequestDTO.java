package com.giro.testePratico.dto.request;

import jakarta.validation.constraints.NotNull;

public record ExchangeRateUpdateRequestDTO(@NotNull
                                           float dailyVariation,

                                           @NotNull
                                           float dailyRate,

                                           @NotNull
                                           Long currencyId) {}
