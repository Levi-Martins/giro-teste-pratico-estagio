package com.giro.testePratico.Services;

import com.giro.testePratico.dto.request.InvestorRequestDTO;
import com.giro.testePratico.dto.response.InvestorResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.entities.Investor;
import com.giro.testePratico.repositories.InvestorRepository;
import com.giro.testePratico.Services.exceptions.EmailAlreadyExistsException;
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
class InvestorServiceTest {

    @InjectMocks
    private InvestorService investorService;

    @Mock
    private InvestorRepository investorRepository;

    private Investor investor;
    private InvestorRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        investor = Investor.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        investor.setId(1L);

        requestDTO = new InvestorRequestDTO("John Doe", "john.doe@example.com");
    }

    @Test
    void getAllInvestors_ShouldReturnPaginatedResponse() {
        Page<Investor> page = new PageImpl<>(List.of(investor));
        when(investorRepository.findAll(any(Pageable.class))).thenReturn(page);

        PaginatedResponseDTO<InvestorResponseDTO> response = investorService.getAllInvestors(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(1, response.totalElements());
        assertEquals("John Doe", response.content().get(0).name());
    }

    @Test
    void findById_ShouldReturnInvestor_WhenExists() {
        when(investorRepository.findById(1L)).thenReturn(Optional.of(investor));

        InvestorResponseDTO response = investorService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("John Doe", response.name());
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(investorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> investorService.findById(1L));
    }

    @Test
    void save_ShouldReturnSavedInvestor() {
        when(investorRepository.existsByEmail(requestDTO.email())).thenReturn(false);
        when(investorRepository.save(any(Investor.class))).thenReturn(investor);

        InvestorResponseDTO response = investorService.save(requestDTO);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("John Doe", response.name());
    }

    @Test
    void save_ShouldThrowException_WhenEmailAlreadyExists() {
        when(investorRepository.existsByEmail(requestDTO.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> investorService.save(requestDTO));
    }

    @Test
    void update_ShouldReturnUpdatedInvestor() {
        when(investorRepository.findById(1L)).thenReturn(Optional.of(investor));
        when(investorRepository.existsByEmail(requestDTO.email())).thenReturn(false);
        when(investorRepository.save(any(Investor.class))).thenReturn(investor);

        InvestorResponseDTO response = investorService.update(1L, requestDTO);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("John Doe", response.name());
    }

    @Test
    void update_ShouldThrowException_WhenInvestorNotFound() {
        when(investorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> investorService.update(1L, requestDTO));
    }

    @Test
    void update_ShouldThrowException_WhenEmailAlreadyExists() {
        when(investorRepository.existsByEmail(requestDTO.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> investorService.update(1L, requestDTO));
    }

    @Test
    void deleteById_ShouldDeleteInvestor() {
        doNothing().when(investorRepository).deleteById(1L);

        assertDoesNotThrow(() -> investorService.deleteById(1L));
        verify(investorRepository, times(1)).deleteById(1L);
    }
}
