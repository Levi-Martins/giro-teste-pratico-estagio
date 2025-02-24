package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.Services.exceptions.EmailAlreadyExistsException;
import com.giro.testePratico.dto.request.InvestorRequestDTO;
import com.giro.testePratico.dto.response.InvestorResponseDTO;
import com.giro.testePratico.entities.Investor;
import com.giro.testePratico.repositories.InvestorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    public List<InvestorResponseDTO> getAllInvestors() {
        return investorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public InvestorResponseDTO findById(Long id) {
        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Investor not found"));
        return toResponseDTO(investor);
    }

    public InvestorResponseDTO save(InvestorRequestDTO investorRequestDTO) {
        if (investorRepository.existsByEmail(investorRequestDTO.email())) {
            throw new EmailAlreadyExistsException("An investor with this email already exists.");
        }

        Investor investor = toEntity(investorRequestDTO);
        Investor savedInvestor = investorRepository.save(investor);
        return toResponseDTO(savedInvestor);
    }

    public InvestorResponseDTO update(Long id, InvestorRequestDTO investorRequestDTO) {
        if (investorRepository.existsByEmail(investorRequestDTO.email())) {
            throw new EmailAlreadyExistsException("An investor with this email already exists.");
        }

        Investor investorToUpdate = investorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Investor not found"));

        investorToUpdate.setName(investorRequestDTO.name());
        investorToUpdate.setEmail(investorRequestDTO.email());

        Investor updatedInvestor = investorRepository.save(investorToUpdate);
        return toResponseDTO(updatedInvestor);
    }

    public void deleteById(Long id) {
        investorRepository.deleteById(id);
    }

    private InvestorResponseDTO toResponseDTO(Investor investor) {
        return new InvestorResponseDTO(investor.getId(), investor.getName(), investor.getEmail());
    }

    private Investor toEntity(InvestorRequestDTO dto) {
        return Investor.builder()
                .name(dto.name())
                .email(dto.email())
                .build();
    }
}
