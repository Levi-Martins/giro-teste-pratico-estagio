package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.entities.ExchangeRate;
import com.giro.testePratico.repositories.ExchangeRateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    public ExchangeRate findById(Long id) {
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findById(id);
        return exchangeRate.orElseThrow(()-> new ObjectNotFoundException("ExchangeRate not found"));
    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }

    public ExchangeRate update(Long id ,ExchangeRate exchangeRate) {
        ExchangeRate exchangeRateToUpdate = findById(id);
        exchangeRateToUpdate.setDate(exchangeRate.getDate());
        exchangeRateToUpdate.setDailyVariantion(exchangeRate.getDailyVariantion());
        exchangeRateToUpdate.setDailyRate(exchangeRate.getDailyRate());
        exchangeRateToUpdate.setCurrency(exchangeRate.getCurrency());
        return exchangeRateRepository.save(exchangeRateToUpdate);
    }

    public void deleteById(Long id) {
        exchangeRateRepository.deleteById(id);
    }
}
