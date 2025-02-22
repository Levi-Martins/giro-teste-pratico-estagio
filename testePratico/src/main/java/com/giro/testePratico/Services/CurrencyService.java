package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency findById(Long id) {
        Optional<Currency> currency = currencyRepository.findById(id);
        return currency.orElseThrow(()-> new ObjectNotFoundException("Currency not found"));
    }

    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Currency update(Long id, Currency currency) {
        Currency currencyToUpdate = findById(id);
        currencyToUpdate.setName(currency.getName());
        currencyToUpdate.setType(currency.getType());
        return currencyRepository.save(currencyToUpdate);
    }

    public void deleteById(Long id) {
        currencyRepository.deleteById(id);
    }
}
