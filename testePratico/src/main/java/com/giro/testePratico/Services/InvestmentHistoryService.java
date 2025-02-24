package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.dto.request.InvestmentHistoryRequestDTO;
import com.giro.testePratico.dto.response.InvestmentHistoryResponseDTO;
import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.entities.InvestmentHistory;
import com.giro.testePratico.entities.Investor;
import com.giro.testePratico.repositories.CurrencyRepository;
import com.giro.testePratico.repositories.InvestmentHistoryRepository;
import com.giro.testePratico.repositories.InvestorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvestmentHistoryService {

    private final InvestmentHistoryRepository investmentHistoryRepository;
    private final CurrencyRepository currencyRepository;
    private final InvestorRepository investorRepository;

    public InvestmentHistoryService(InvestmentHistoryRepository investmentHistoryRepository,
                                    CurrencyRepository currencyRepository,
                                    InvestorRepository investorRepository) {
        this.investmentHistoryRepository = investmentHistoryRepository;
        this.currencyRepository = currencyRepository;
        this.investorRepository = investorRepository;
    }

    public List<InvestmentHistoryResponseDTO> getAllInvestmentHistory() {
        return investmentHistoryRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public InvestmentHistoryResponseDTO findById(Long id) {
        InvestmentHistory investmentHistory = investmentHistoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("InvestmentHistory not found"));
        return toResponseDTO(investmentHistory);
    }

    public InvestmentHistoryResponseDTO save(InvestmentHistoryRequestDTO requestDTO) {
        InvestmentHistory investmentHistory = toEntity(requestDTO);
        InvestmentHistory savedInvestment = investmentHistoryRepository.save(investmentHistory);
        return toResponseDTO(savedInvestment);
    }

    public InvestmentHistoryResponseDTO update(Long id, InvestmentHistoryRequestDTO requestDTO) {
        InvestmentHistory investmentHistoryToUpdate = investmentHistoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("InvestmentHistory not found"));

        investmentHistoryToUpdate.setInitialAmount(requestDTO.initialAmount());
        investmentHistoryToUpdate.setMonths(requestDTO.months());
        investmentHistoryToUpdate.setInterestRate(requestDTO.interestRate());
        investmentHistoryToUpdate.setFinalAmount(requestDTO.finalAmount());
        investmentHistoryToUpdate.setCurrency(getCurrencyById(requestDTO.currencyId()));
        investmentHistoryToUpdate.setInvestor(getInvestorById(requestDTO.investorId()));

        InvestmentHistory updatedInvestment = investmentHistoryRepository.save(investmentHistoryToUpdate);
        return toResponseDTO(updatedInvestment);
    }

    public void deleteById(Long id) {
        investmentHistoryRepository.deleteById(id);
    }

    private InvestmentHistoryResponseDTO toResponseDTO(InvestmentHistory investmentHistory) {
        return new InvestmentHistoryResponseDTO(
                investmentHistory.getId(),
                investmentHistory.getInitialAmount(),
                investmentHistory.getMonths(),
                investmentHistory.getInterestRate(),
                investmentHistory.getFinalAmount(),
                investmentHistory.getCurrency().getId(),
                investmentHistory.getInvestor().getId()
        );
    }

    private InvestmentHistory toEntity(InvestmentHistoryRequestDTO dto) {
        return InvestmentHistory.builder()
                .initialAmount(dto.initialAmount())
                .months(dto.months())
                .interestRate(dto.interestRate())
                .finalAmount(dto.finalAmount())
                .currency(getCurrencyById(dto.currencyId()))
                .investor(getInvestorById(dto.investorId()))
                .build();
    }

    private Currency getCurrencyById(Long id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Currency not found"));
    }

    private Investor getInvestorById(Long id) {
        return investorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Investor not found"));
    }
}
