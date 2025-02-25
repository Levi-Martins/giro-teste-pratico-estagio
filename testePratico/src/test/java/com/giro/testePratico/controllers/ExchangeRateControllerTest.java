package com.giro.testePratico.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giro.testePratico.dto.request.ExchangeRateRequestDTO;
import com.giro.testePratico.dto.request.ExchangeRateUpdateRequestDTO;
import com.giro.testePratico.dto.response.ExchangeRateResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.Services.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExchangeRateController.class)
@ExtendWith(MockitoExtension.class)
class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExchangeRateResponseDTO exchangeRateResponseDTO;
    private ExchangeRateRequestDTO exchangeRateRequestDTO;
    private ExchangeRateUpdateRequestDTO exchangeRateUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        exchangeRateResponseDTO = new ExchangeRateResponseDTO(1L, LocalDate.now(), 1.2f, 5.5f, 1L);
        exchangeRateRequestDTO = new ExchangeRateRequestDTO(LocalDate.now(), 1.2f, 5.5f, 1L);
        exchangeRateUpdateRequestDTO = new ExchangeRateUpdateRequestDTO(1.5f, 6.0f, 1L);
    }

    @Test
    void getAll_ShouldReturnPaginatedResponse() throws Exception {
        PaginatedResponseDTO<ExchangeRateResponseDTO> paginatedResponse = new PaginatedResponseDTO<>(
                List.of(exchangeRateResponseDTO), 0, 10, 1, 1
        );
        Mockito.when(exchangeRateService.getAllExchangeRates(any(PageRequest.class))).thenReturn(paginatedResponse);

        mockMvc.perform(get("/exchange-rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void findById_ShouldReturnExchangeRate_WhenExists() throws Exception {
        Mockito.when(exchangeRateService.findById(1L)).thenReturn(exchangeRateResponseDTO);

        mockMvc.perform(get("/exchange-rates/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void save_ShouldReturnCreatedExchangeRate() throws Exception {
        Mockito.when(exchangeRateService.save(any(ExchangeRateRequestDTO.class))).thenReturn(exchangeRateResponseDTO);

        mockMvc.perform(post("/exchange-rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exchangeRateRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void update_ShouldReturnUpdatedExchangeRate() throws Exception {
        Mockito.when(exchangeRateService.update(eq(1L), any(ExchangeRateUpdateRequestDTO.class))).thenReturn(exchangeRateResponseDTO);

        mockMvc.perform(put("/exchange-rates/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exchangeRateUpdateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deleteById_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(exchangeRateService).deleteById(1L);

        mockMvc.perform(delete("/exchange-rates/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOlderThan30Days_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(exchangeRateService).deleteOlderThan30Days();

        mockMvc.perform(delete("/exchange-rates/old"))
                .andExpect(status().isNoContent());
    }
}
