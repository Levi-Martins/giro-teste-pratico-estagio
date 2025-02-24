package com.giro.testePratico.controllers;

import com.giro.testePratico.dto.request.CurrencyRequestDTO;
import com.giro.testePratico.dto.response.CurrencyResponseDTO;
import com.giro.testePratico.Services.CurrencyService;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<CurrencyResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(currencyService.getAllCurrencies(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(currencyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CurrencyResponseDTO> save(@RequestBody @Valid CurrencyRequestDTO currencyRequestDTO) {
        return new ResponseEntity<>(currencyService.save(currencyRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CurrencyRequestDTO currencyRequestDTO) {
        return ResponseEntity.ok(currencyService.update(id, currencyRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        currencyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
