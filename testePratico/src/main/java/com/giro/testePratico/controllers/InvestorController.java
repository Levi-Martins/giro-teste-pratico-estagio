package com.giro.testePratico.controllers;

import com.giro.testePratico.Services.InvestorService;
import com.giro.testePratico.entities.Investor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("investor")
public class InvestorController {

    private final InvestorService investorService;

    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @GetMapping
    public ResponseEntity<List<Investor>>  getAll() {
        return ResponseEntity.ok(investorService.getAllInvestors());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Investor> getById(@PathVariable Long id) {
        return ResponseEntity.ok(investorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Investor> save(@RequestBody Investor investor) {
        return new ResponseEntity<>(investorService.save(investor), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Investor> update(@RequestBody Investor investor, @PathVariable Long id) {
        return new ResponseEntity<>(investorService.update(id, investor), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Investor> deleteById(@PathVariable Long id) {
        investorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
