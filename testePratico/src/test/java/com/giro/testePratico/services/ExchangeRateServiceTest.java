package com.giro.testePratico.services;

import com.giro.testePratico.dto.request.ExchangeRateRequestDTO;
import com.giro.testePratico.dto.request.ExchangeRateUpdateRequestDTO;
import com.giro.testePratico.dto.response.ExchangeRateResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.entities.ExchangeRate;
import com.giro.testePratico.repositories.CurrencyRepository;
import com.giro.testePratico.repositories.ExchangeRateRepository;
import com.giro.testePratico.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    private ExchangeRate exchangeRate;
    private Currency currency;
    private ExchangeRateRequestDTO requestDTO;
    private ExchangeRateUpdateRequestDTO updateRequestDTO;

    @BeforeEach
    void setUp() {
        currency = Currency.builder()
                .name("USD")
                .type("Dollar")
                .build();

        exchangeRate = ExchangeRate.builder()
                .date(LocalDate.now())
                .dailyVariantion(1.2f)
                .dailyRate(1.3f)
                .currency(currency)
                .build();

        requestDTO = new ExchangeRateRequestDTO(LocalDate.now(), 1.2f, 1.3f, 1L);
        updateRequestDTO = new ExchangeRateUpdateRequestDTO(1.5f, 1.6f, 1L);
    }

    @Test
    void shouldGetAllExchangeRates() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ExchangeRate> page = new PageImpl<>(List.of(exchangeRate));

        when(exchangeRateRepository.findAll(pageable)).thenReturn(page);

        PaginatedResponseDTO<ExchangeRateResponseDTO> result = exchangeRateService.getAllExchangeRates(pageable);

        assertNotNull(result);
        assertEquals(1, result.content().size());
        verify(exchangeRateRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldFindByIdWhenExists() {
        exchangeRate.setId(1L);

        when(exchangeRateRepository.findById(1L)).thenReturn(Optional.of(exchangeRate));

        ExchangeRateResponseDTO result = exchangeRateService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(exchangeRateRepository, times(1)).findById(1L);
    }


    @Test
    void shouldThrowExceptionWhenExchangeRateNotFound() {
        when(exchangeRateRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> exchangeRateService.findById(2L));
    }

    @Test
    void shouldSaveExchangeRate() {
        currency.setId(1L);

        when(currencyRepository.findById(1L)).thenReturn(Optional.of(currency));

        when(exchangeRateRepository.save(any())).thenAnswer(invocation -> {
            ExchangeRate savedExchangeRate = invocation.getArgument(0);
            savedExchangeRate.setId(1L);
            return savedExchangeRate;
        });

        ExchangeRateResponseDTO result = exchangeRateService.save(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(exchangeRateRepository, times(1)).save(any());
    }


    @Test
    void shouldUpdateExchangeRate() {
        when(exchangeRateRepository.findById(1L)).thenReturn(Optional.of(exchangeRate));
        when(currencyRepository.findById(1L)).thenReturn(Optional.of(currency));
        when(exchangeRateRepository.save(any())).thenReturn(exchangeRate);

        ExchangeRateResponseDTO result = exchangeRateService.update(1L, updateRequestDTO);

        assertNotNull(result);
        verify(exchangeRateRepository, times(1)).save(any());
    }

    @Test
    void shouldDeleteById() {
        doNothing().when(exchangeRateRepository).deleteById(1L);

        exchangeRateService.deleteById(1L);

        verify(exchangeRateRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldGetLast7DaysExchangeRates() {
        when(exchangeRateRepository.findByDateAfter(any())).thenReturn(List.of(exchangeRate));

        List<ExchangeRateResponseDTO> result = exchangeRateService.getLast7DaysExchangeRates();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(exchangeRateRepository, times(1)).findByDateAfter(any());
    }

    @Test
    void shouldDeleteOlderThan30Days() {
        doNothing().when(exchangeRateRepository).deleteByDateBefore(any());

        exchangeRateService.deleteOlderThan30Days();

        verify(exchangeRateRepository, times(1)).deleteByDateBefore(any());
    }
}