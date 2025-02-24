package com.giro.testePratico.dto.request;

import jakarta.validation.constraints.NotNull;

public record InvestmentHistoryRequestDTO(@NotNull float initialAmount,
                                          @NotNull int months,
                                          @NotNull float interestRate,
                                          @NotNull float finalAmount,
                                          @NotNull Long currencyId,
                                          @NotNull Long investorId) {
}
