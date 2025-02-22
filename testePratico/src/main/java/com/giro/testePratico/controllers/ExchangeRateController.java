package com.giro.testePratico.controllers;

import com.giro.testePratico.Services.ExchangeRateService;
import com.giro.testePratico.entities.ExchangeRate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("exchangeRate")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<List<ExchangeRate>> getAll() {
        return ResponseEntity.ok(exchangeRateService.getAllExchangeRates());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExchangeRate> findById(@PathVariable Long id) {
        return ResponseEntity.ok(exchangeRateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExchangeRate> save(@RequestBody ExchangeRate exchangeRate) {
        return new ResponseEntity<>(exchangeRateService.save(exchangeRate), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ExchangeRate> update(@PathVariable Long id, @RequestBody ExchangeRate exchangeRate) {
        return new ResponseEntity<>(exchangeRateService.update(id, exchangeRate), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ExchangeRate> deleteById(@PathVariable Long id) {
        exchangeRateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
