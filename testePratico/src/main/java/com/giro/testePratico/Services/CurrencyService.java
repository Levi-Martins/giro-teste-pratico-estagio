package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.dto.request.CurrencyRequestDTO;
import com.giro.testePratico.dto.response.CurrencyResponseDTO;
import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<CurrencyResponseDTO> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CurrencyResponseDTO findById(Long id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Currency not found"));
        return toResponseDTO(currency);
    }

    public CurrencyResponseDTO save(CurrencyRequestDTO currencyRequestDTO) {
        Currency currency = toEntity(currencyRequestDTO);
        Currency savedCurrency = currencyRepository.save(currency);
        return toResponseDTO(savedCurrency);
    }

    public CurrencyResponseDTO update(Long id, CurrencyRequestDTO currencyRequestDTO) {
        Currency currencyToUpdate = currencyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Currency not found"));

        currencyToUpdate.setName(currencyRequestDTO.name());
        currencyToUpdate.setType(currencyRequestDTO.type());

        Currency updatedCurrency = currencyRepository.save(currencyToUpdate);
        return toResponseDTO(updatedCurrency);
    }

    public void deleteById(Long id) {
        currencyRepository.deleteById(id);
    }

    private CurrencyResponseDTO toResponseDTO(Currency currency) {
        return new CurrencyResponseDTO(currency.getId(), currency.getName(), currency.getType());
    }

    private Currency toEntity(CurrencyRequestDTO dto) {
        return Currency.builder()
                .name(dto.name())
                .type(dto.type())
                .build();
    }
}
