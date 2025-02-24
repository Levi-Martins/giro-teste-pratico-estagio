package com.giro.testePratico.controllers;

import com.giro.testePratico.Services.InvestmentHistoryService;
import com.giro.testePratico.dto.request.InvestmentHistoryRequestDTO;
import com.giro.testePratico.dto.response.InvestmentHistoryResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("investments")
public class InvestmentHistoryController {

    private final InvestmentHistoryService investmentHistoryService;

    public InvestmentHistoryController(InvestmentHistoryService investmentHistoryService) {
        this.investmentHistoryService = investmentHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<InvestmentHistoryResponseDTO>> getAll() {
        return ResponseEntity.ok(investmentHistoryService.getAllInvestmentHistory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentHistoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(investmentHistoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InvestmentHistoryResponseDTO> save(@RequestBody @Valid InvestmentHistoryRequestDTO requestDTO) {
        return new ResponseEntity<>(investmentHistoryService.save(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestmentHistoryResponseDTO> update(@PathVariable Long id, @RequestBody @Valid InvestmentHistoryRequestDTO requestDTO) {
        return ResponseEntity.ok(investmentHistoryService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        investmentHistoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
