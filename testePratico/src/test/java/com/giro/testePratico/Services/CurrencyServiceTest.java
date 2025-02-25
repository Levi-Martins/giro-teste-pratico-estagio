package com.giro.testePratico.Services;

import com.giro.testePratico.dto.request.CurrencyRequestDTO;
import com.giro.testePratico.dto.response.CurrencyResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.repositories.CurrencyRepository;
import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepository currencyRepository;

    private Currency currency;
    private CurrencyRequestDTO currencyRequestDTO;

    @BeforeEach
    void setUp() {
        currency = Currency.builder()
                .name("Dollar")
                .type("Fiat")
                .build();
        currency.setId(1L);
        currencyRequestDTO = new CurrencyRequestDTO("Euro", "Fiat");
    }

    @Test
    void getAllCurrencies_ShouldReturnPaginatedResponse() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Currency> page = new PageImpl<>(List.of(currency), pageable, 1);
        when(currencyRepository.findAll(pageable)).thenReturn(page);

        PaginatedResponseDTO<CurrencyResponseDTO> response = currencyService.getAllCurrencies(pageable);

        assertNotNull(response);
        assertEquals(1, response.totalElements());
        verify(currencyRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_ShouldReturnCurrencyResponseDTO_WhenFound() {
        when(currencyRepository.findById(1L)).thenReturn(Optional.of(currency));

        CurrencyResponseDTO response = currencyService.findById(1L);

        assertNotNull(response);
        assertEquals("Dollar", response.name());
        verify(currencyRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(currencyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> currencyService.findById(1L));
        verify(currencyRepository, times(1)).findById(1L);
    }

    @Test
    void save_ShouldReturnSavedCurrency() {
        when(currencyRepository.save(any(Currency.class))).thenReturn(currency);

        CurrencyResponseDTO response = currencyService.save(currencyRequestDTO);

        assertNotNull(response);
        assertEquals("Dollar", response.name());
        verify(currencyRepository, times(1)).save(any(Currency.class));
    }

    @Test
    void update_ShouldReturnUpdatedCurrency_WhenFound() {
        when(currencyRepository.findById(1L)).thenReturn(Optional.of(currency));
        when(currencyRepository.save(any(Currency.class))).thenReturn(currency);

        CurrencyResponseDTO response = currencyService.update(1L, currencyRequestDTO);

        assertNotNull(response);
        verify(currencyRepository, times(1)).save(any(Currency.class));
    }

    @Test
    void update_ShouldThrowException_WhenNotFound() {
        when(currencyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> currencyService.update(1L, currencyRequestDTO));
    }

    @Test
    void deleteById_ShouldDelete_WhenExists() {
        doNothing().when(currencyRepository).deleteById(1L);

        assertDoesNotThrow(() -> currencyService.deleteById(1L));
        verify(currencyRepository, times(1)).deleteById(1L);
    }
}
