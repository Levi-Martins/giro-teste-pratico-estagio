package com.giro.testePratico.dto.response;

public record InvestmentHistoryResponseDTO(Long id,
                                           float initialAmount,
                                           int months,
                                           float interestRate,
                                           float finalAmount,
                                           Long currencyId,
                                           Long investorId) {}
