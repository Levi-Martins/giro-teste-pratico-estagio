package com.giro.testePratico.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giro.testePratico.dto.request.CurrencyRequestDTO;
import com.giro.testePratico.dto.response.CurrencyResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.Services.CurrencyService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurrencyController.class)
@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Autowired
    private ObjectMapper objectMapper;

    private CurrencyResponseDTO currencyResponseDTO;
    private CurrencyRequestDTO currencyRequestDTO;

    @BeforeEach
    void setUp() {
        currencyResponseDTO = new CurrencyResponseDTO(1L, "Dollar", "Fiat");
        currencyRequestDTO = new CurrencyRequestDTO("Euro", "Fiat");
    }

    @Test
    void getAll_ShouldReturnPaginatedResponse() throws Exception {
        PaginatedResponseDTO<CurrencyResponseDTO> paginatedResponse = new PaginatedResponseDTO<>(
                List.of(currencyResponseDTO), 0, 10, 1, 1
        );
        Mockito.when(currencyService.getAllCurrencies(any(PageRequest.class))).thenReturn(paginatedResponse);

        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void findById_ShouldReturnCurrency_WhenExists() throws Exception {
        Mockito.when(currencyService.findById(1L)).thenReturn(currencyResponseDTO);

        mockMvc.perform(get("/currencies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dollar"));
    }

    @Test
    void save_ShouldReturnCreatedCurrency() throws Exception {
        Mockito.when(currencyService.save(any(CurrencyRequestDTO.class))).thenReturn(currencyResponseDTO);

        mockMvc.perform(post("/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Dollar"));
    }

    @Test
    void update_ShouldReturnUpdatedCurrency() throws Exception {
        Mockito.when(currencyService.update(Mockito.eq(1L), any(CurrencyRequestDTO.class))).thenReturn(currencyResponseDTO);

        mockMvc.perform(put("/currencies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dollar"));
    }

    @Test
    void deleteById_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(currencyService).deleteById(1L);

        mockMvc.perform(delete("/currencies/1"))
                .andExpect(status().isNoContent());
    }
}
