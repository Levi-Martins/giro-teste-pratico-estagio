package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.entities.InvestmentHistory;
import com.giro.testePratico.repositories.InvestmentHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestmentHistoryService {

    private final InvestmentHistoryRepository investmentHistoryRepository;


    public InvestmentHistoryService(InvestmentHistoryRepository investmentHistoryRepository) {
        this.investmentHistoryRepository = investmentHistoryRepository;
    }

    public List<InvestmentHistory> getAllInvestmentHistory() {
        return investmentHistoryRepository.findAll();
    }

    public InvestmentHistory findById(Long id) {
        Optional<InvestmentHistory> investmentHistory = investmentHistoryRepository.findById(id);
        return investmentHistory.orElseThrow(()-> new ObjectNotFoundException("InvestmentHistory not found"));
    }

    public InvestmentHistory save(InvestmentHistory investmentHistory) {
        return investmentHistoryRepository.save(investmentHistory);
    }

    public InvestmentHistory update(Long id, InvestmentHistory investmentHistory) {
        InvestmentHistory investmentHistoryToUpdate = findById(id);
        investmentHistoryToUpdate.setInitialAmount(investmentHistory.getInitialAmount());
        investmentHistoryToUpdate.setMonths(investmentHistory.getMonths());
        investmentHistoryToUpdate.setInterestRate(investmentHistory.getInterestRate());
        investmentHistoryToUpdate.setFinalAmount(investmentHistory.getFinalAmount());
        investmentHistoryToUpdate.setCurrency(investmentHistory.getCurrency());
        investmentHistoryToUpdate.setInvestor(investmentHistory.getInvestor());
        return investmentHistoryRepository.save(investmentHistoryToUpdate);
    }

    public void deleteById(Long id) {
        investmentHistoryRepository.deleteById(id);
    }
}
