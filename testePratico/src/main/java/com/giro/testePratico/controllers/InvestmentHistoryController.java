package com.giro.testePratico.controllers;

import com.giro.testePratico.Services.InvestmentHistoryService;
import com.giro.testePratico.entities.InvestmentHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("investmentHistory")
public class InvestmentHistoryController {

    private final InvestmentHistoryService investmentHistoryService;

    public InvestmentHistoryController(InvestmentHistoryService investmentHistoryService) {
        this.investmentHistoryService = investmentHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<InvestmentHistory>> getAll() {
        return ResponseEntity.ok(investmentHistoryService.getAllInvestmentHistory());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InvestmentHistory> getById(@PathVariable Long id) {
        return ResponseEntity.ok(investmentHistoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InvestmentHistory> save(@RequestBody InvestmentHistory investmentHistory) {
        return new ResponseEntity<>(investmentHistoryService.save(investmentHistory), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<InvestmentHistory> update(@PathVariable Long id, @RequestBody InvestmentHistory investmentHistory) {
        return new ResponseEntity<>(investmentHistoryService.update(id, investmentHistory), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<InvestmentHistory> deleteById(@PathVariable Long id) {
        investmentHistoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
