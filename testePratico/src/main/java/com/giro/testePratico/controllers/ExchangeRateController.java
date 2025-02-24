package com.giro.testePratico.controllers;

import com.giro.testePratico.Services.ExchangeRateService;
import com.giro.testePratico.dto.request.ExchangeRateRequestDTO;
import com.giro.testePratico.dto.request.ExchangeRateUpdateRequestDTO;
import com.giro.testePratico.dto.response.ExchangeRateResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("exchange-rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<ExchangeRateResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(exchangeRateService.getAllExchangeRates(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExchangeRateResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(exchangeRateService.findById(id));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ExchangeRateResponseDTO>> getLast7DaysExchangeRates() {
        return ResponseEntity.ok(exchangeRateService.getLast7DaysExchangeRates());
    }

    @PostMapping
    public ResponseEntity<ExchangeRateResponseDTO> save(@RequestBody @Valid ExchangeRateRequestDTO exchangeRateRequestDTO) {
        return new ResponseEntity<>(exchangeRateService.save(exchangeRateRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ExchangeRateResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid ExchangeRateUpdateRequestDTO exchangeRateUpdateRequestDTO) {
        return new ResponseEntity<>(exchangeRateService.update(id, exchangeRateUpdateRequestDTO), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        exchangeRateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/old")
    public ResponseEntity<Void> deleteOlderThan30Days() {
        exchangeRateService.deleteOlderThan30Days();
        return ResponseEntity.noContent().build();
    }
}
