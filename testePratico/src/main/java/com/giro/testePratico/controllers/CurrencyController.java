package com.giro.testePratico.controllers;

import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.Services.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<List<Currency>> getAll() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Currency> findById(@PathVariable Long id) {
        return ResponseEntity.ok(currencyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Currency> save(@RequestBody Currency currency) {
        return new ResponseEntity<>(currencyService.save(currency), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Currency> update(@PathVariable Long id, @RequestBody Currency currency) {
        return ResponseEntity.ok(currencyService.update(id, currency));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Currency> deleteById(@PathVariable Long id) {
        currencyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
