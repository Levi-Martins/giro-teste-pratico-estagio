package com.giro.testePratico.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giro.testePratico.dto.request.InvestmentHistoryRequestDTO;
import com.giro.testePratico.dto.response.InvestmentHistoryResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.services.InvestmentHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvestmentHistoryController.class)
@ExtendWith(MockitoExtension.class)
class InvestmentHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestmentHistoryService investmentHistoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private InvestmentHistoryResponseDTO investmentHistoryResponseDTO;
    private InvestmentHistoryRequestDTO investmentHistoryRequestDTO;

    @BeforeEach
    void setUp() {
        investmentHistoryResponseDTO = new InvestmentHistoryResponseDTO(1L, 1000.0f, 12, 5.0f, 1100.0f, 1L, 1L);
        investmentHistoryRequestDTO = new InvestmentHistoryRequestDTO(1000.0f, 12, 5.0f, 1100.0f, 1L, 1L);
    }

    @Test
    void getAll_ShouldReturnPaginatedResponse() throws Exception {
        PaginatedResponseDTO<InvestmentHistoryResponseDTO> paginatedResponse =
                new PaginatedResponseDTO<>(List.of(investmentHistoryResponseDTO), 0, 10, 1, 1);

        Mockito.when(investmentHistoryService.getAllInvestmentHistory(any())).thenReturn(paginatedResponse);

        mockMvc.perform(get("/investments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void getById_ShouldReturnInvestmentHistory() throws Exception {
        Mockito.when(investmentHistoryService.findById(1L)).thenReturn(investmentHistoryResponseDTO);

        mockMvc.perform(get("/investments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void save_ShouldReturnCreatedInvestmentHistory() throws Exception {
        Mockito.when(investmentHistoryService.save(any(InvestmentHistoryRequestDTO.class)))
                .thenReturn(investmentHistoryResponseDTO);

        mockMvc.perform(post("/investments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(investmentHistoryRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_ShouldReturnUpdatedInvestmentHistory() throws Exception {
        Mockito.when(investmentHistoryService.update(Mockito.eq(1L), any(InvestmentHistoryRequestDTO.class)))
                .thenReturn(investmentHistoryResponseDTO);

        mockMvc.perform(put("/investments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(investmentHistoryRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteById_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(investmentHistoryService).deleteById(1L);

        mockMvc.perform(delete("/investments/1"))
                .andExpect(status().isNoContent());
    }
}
