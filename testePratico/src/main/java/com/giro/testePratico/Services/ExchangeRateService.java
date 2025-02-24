package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.dto.request.ExchangeRateRequestDTO;
import com.giro.testePratico.dto.request.ExchangeRateUpdateRequestDTO;
import com.giro.testePratico.dto.response.ExchangeRateResponseDTO;
import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.entities.ExchangeRate;
import com.giro.testePratico.repositories.CurrencyRepository;
import com.giro.testePratico.repositories.ExchangeRateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository, CurrencyRepository currencyRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
    }

    public List<ExchangeRateResponseDTO> getAllExchangeRates() {
        return exchangeRateRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ExchangeRateResponseDTO findById(Long id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("ExchangeRate not found"));
        return toResponseDTO(exchangeRate);
    }

    public ExchangeRateResponseDTO save(ExchangeRateRequestDTO dto) {
        Currency currency = currencyRepository.findById(dto.currencyId())
                .orElseThrow(() -> new ObjectNotFoundException("Currency not found"));

        ExchangeRate exchangeRate = toEntity(dto, currency);
        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRate);
        return toResponseDTO(savedExchangeRate);
    }

    public ExchangeRateResponseDTO update(Long id, ExchangeRateUpdateRequestDTO dto) {
        ExchangeRate exchangeRateToUpdate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("ExchangeRate not found"));

        Currency currency = currencyRepository.findById(dto.currencyId())
                .orElseThrow(() -> new ObjectNotFoundException("Currency not found"));

        exchangeRateToUpdate.setDailyVariantion(dto.dailyVariation());
        exchangeRateToUpdate.setDailyRate(dto.dailyRate());
        exchangeRateToUpdate.setCurrency(currency);

        ExchangeRate updatedExchangeRate = exchangeRateRepository.save(exchangeRateToUpdate);
        return toResponseDTO(updatedExchangeRate);
    }

    public void deleteById(Long id) {
        exchangeRateRepository.deleteById(id);
    }

    private ExchangeRateResponseDTO toResponseDTO(ExchangeRate exchangeRate) {
        return new ExchangeRateResponseDTO(
                exchangeRate.getId(),
                exchangeRate.getDate(),
                exchangeRate.getDailyRate(),
                exchangeRate.getDailyRate(),
                exchangeRate.getCurrency().getId()
        );
    }

    private ExchangeRate toEntity(ExchangeRateRequestDTO dto, Currency currency) {
        return ExchangeRate.builder()
                .date(dto.date())
                .dailyVariantion(dto.dailyVariation())
                .dailyRate(dto.dailyRate())
                .currency(currency)
                .build();
    }

    public List<ExchangeRateResponseDTO> getLast7DaysExchangeRates() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findByDateAfter(sevenDaysAgo);

        return exchangeRates.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteOlderThan30Days() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        exchangeRateRepository.deleteByDateBefore(thirtyDaysAgo);
    }
}
