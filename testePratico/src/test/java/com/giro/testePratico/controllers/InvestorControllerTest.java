package com.giro.testePratico.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giro.testePratico.dto.request.InvestorRequestDTO;
import com.giro.testePratico.dto.response.InvestorResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.Services.InvestorService;
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

@WebMvcTest(InvestorController.class)
@ExtendWith(MockitoExtension.class)
class InvestorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestorService investorService;

    @Autowired
    private ObjectMapper objectMapper;

    private InvestorResponseDTO investorResponseDTO;
    private InvestorRequestDTO investorRequestDTO;

    @BeforeEach
    void setUp() {
        investorResponseDTO = new InvestorResponseDTO(1L, "John Doe", "john.doe@example.com");
        investorRequestDTO = new InvestorRequestDTO("John Doe", "john.doe@example.com");
    }

    @Test
    void getAll_ShouldReturnPaginatedResponse() throws Exception {
        PaginatedResponseDTO<InvestorResponseDTO> paginatedResponse =
                new PaginatedResponseDTO<>(List.of(investorResponseDTO), 0, 10, 1, 1);

        Mockito.when(investorService.getAllInvestors(any())).thenReturn(paginatedResponse);

        mockMvc.perform(get("/investors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void findById_ShouldReturnInvestor() throws Exception {
        Mockito.when(investorService.findById(1L)).thenReturn(investorResponseDTO);

        mockMvc.perform(get("/investors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void save_ShouldReturnCreatedInvestor() throws Exception {
        Mockito.when(investorService.save(any(InvestorRequestDTO.class)))
                .thenReturn(investorResponseDTO);

        mockMvc.perform(post("/investors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(investorRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void update_ShouldReturnUpdatedInvestor() throws Exception {
        Mockito.when(investorService.update(Mockito.eq(1L), any(InvestorRequestDTO.class)))
                .thenReturn(investorResponseDTO);

        mockMvc.perform(put("/investors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(investorRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void deleteById_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(investorService).deleteById(1L);

        mockMvc.perform(delete("/investors/1"))
                .andExpect(status().isNoContent());
    }
}
