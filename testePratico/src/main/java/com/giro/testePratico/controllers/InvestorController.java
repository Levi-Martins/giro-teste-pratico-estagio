package com.giro.testePratico.controllers;

import com.giro.testePratico.dto.request.InvestorRequestDTO;
import com.giro.testePratico.dto.response.InvestorResponseDTO;
import com.giro.testePratico.Services.InvestorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("investors")
public class InvestorController {

    private final InvestorService investorService;

    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @GetMapping
    public ResponseEntity<List<InvestorResponseDTO>> getAll() {
        return ResponseEntity.ok(investorService.getAllInvestors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(investorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InvestorResponseDTO> save(@RequestBody @Valid InvestorRequestDTO investorRequestDTO) {
        return new ResponseEntity<>(investorService.save(investorRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestorResponseDTO> update(@PathVariable Long id, @RequestBody @Valid InvestorRequestDTO investorRequestDTO) {
        return ResponseEntity.ok(investorService.update(id, investorRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        investorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
