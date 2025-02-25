package com.giro.testePratico.services;

import com.giro.testePratico.services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.dto.request.CurrencyRequestDTO;
import com.giro.testePratico.dto.response.CurrencyResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.entities.Currency;
import com.giro.testePratico.repositories.CurrencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public PaginatedResponseDTO<CurrencyResponseDTO> getAllCurrencies(Pageable pageable) {
        Page<Currency> page = currencyRepository.findAll(pageable);

        List<CurrencyResponseDTO> content = page.getContent().stream()
                .map(this::toResponseDTO)
                .toList();

        return new PaginatedResponseDTO<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
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
