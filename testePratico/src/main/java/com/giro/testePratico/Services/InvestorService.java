package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.entities.Investor;
import com.giro.testePratico.repositories.InvestorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    public List<Investor> getAllInvestors() {
        return investorRepository.findAll();
    }

    public Investor findById(Long id) {
        Optional<Investor> investor = investorRepository.findById(id);
        return investor.orElseThrow(()-> new ObjectNotFoundException("Investor not found"));
    }

    public Investor save(Investor investor) {
        return investorRepository.save(investor);
    }

    public Investor update(Long id, Investor investor) {
        Investor investorToUpdate = findById(id);
        investorToUpdate.setName(investor.getName());
        investorToUpdate.setEmail(investor.getEmail());
        return investorRepository.save(investorToUpdate);
    }

    public void deleteById(Long id) {
        investorRepository.deleteById(id);
    }
}
