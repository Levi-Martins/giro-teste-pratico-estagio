package com.giro.testePratico.services;

import com.giro.testePratico.dto.request.InvestmentHistoryRequestDTO;
import com.giro.testePratico.dto.response.InvestmentHistoryResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.entities.InvestmentHistory;
import com.giro.testePratico.entities.Investor;
import com.giro.testePratico.repositories.CurrencyRepository;
import com.giro.testePratico.repositories.InvestmentHistoryRepository;
import com.giro.testePratico.repositories.InvestorRepository;
import com.giro.testePratico.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestmentHistoryServiceTest {

    @InjectMocks
    private InvestmentHistoryService investmentHistoryService;

    @Mock
    private InvestmentHistoryRepository investmentHistoryRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private InvestorRepository investorRepository;

    private InvestmentHistory investmentHistory;
    private InvestmentHistoryRequestDTO requestDTO;
    private Currency currency;
    private Investor investor;

    @BeforeEach
    void setUp() {
        currency = Currency.builder()
                .name("Dollar")
                .type("Fiat")
                .build();

        investor = Investor.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        investmentHistory = InvestmentHistory.builder()
                .initialAmount(1000.0f)
                .months(12)
                .interestRate(5.0f)
                .finalAmount(1050.0f)
                .currency(currency)
                .investor(investor)
                .build();

        investmentHistory.setId(1L);

        requestDTO = new InvestmentHistoryRequestDTO(1000f, 12, 5.0f, 1500f, 1L, 1L);
    }




    @Test
    void getAllInvestmentHistory_ShouldReturnPaginatedResponse() {
        Page<InvestmentHistory> page = new PageImpl<>(List.of(investmentHistory));
        when(investmentHistoryRepository.findAll(any(Pageable.class))).thenReturn(page);

        PaginatedResponseDTO<InvestmentHistoryResponseDTO> response = investmentHistoryService.getAllInvestmentHistory(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(1, response.totalElements());
    }

    @Test
    void findById_ShouldReturnInvestmentHistory_WhenExists() {
        when(investmentHistoryRepository.findById(1L)).thenReturn(Optional.of(investmentHistory));

        InvestmentHistoryResponseDTO response = investmentHistoryService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(investmentHistoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> investmentHistoryService.findById(1L));
    }

    @Test
    void save_ShouldReturnSavedInvestmentHistory() {
        when(currencyRepository.findById(1L)).thenReturn(Optional.of(currency));
        when(investorRepository.findById(1L)).thenReturn(Optional.of(investor));
        when(investmentHistoryRepository.save(any(InvestmentHistory.class))).thenReturn(investmentHistory);

        InvestmentHistoryResponseDTO response = investmentHistoryService.save(requestDTO);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    void update_ShouldReturnUpdatedInvestmentHistory() {
        when(investmentHistoryRepository.findById(1L)).thenReturn(Optional.of(investmentHistory));
        when(currencyRepository.findById(1L)).thenReturn(Optional.of(currency));
        when(investorRepository.findById(1L)).thenReturn(Optional.of(investor));
        when(investmentHistoryRepository.save(any(InvestmentHistory.class))).thenReturn(investmentHistory);

        InvestmentHistoryResponseDTO response = investmentHistoryService.update(1L, requestDTO);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    void update_ShouldThrowException_WhenNotFound() {
        when(investmentHistoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> investmentHistoryService.update(1L, requestDTO));
    }

    @Test
    void deleteById_ShouldDeleteInvestmentHistory() {
        doNothing().when(investmentHistoryRepository).deleteById(1L);

        assertDoesNotThrow(() -> investmentHistoryService.deleteById(1L));
        verify(investmentHistoryRepository, times(1)).deleteById(1L);
    }
}
