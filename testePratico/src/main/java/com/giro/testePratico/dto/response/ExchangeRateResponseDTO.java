package com.giro.testePratico.dto.response;

import java.time.LocalDate;

public record ExchangeRateResponseDTO(Long id,
                                      LocalDate date,
                                      float dailyVariation,
                                      float dailyRate,
                                      Long currencyId) {
}
