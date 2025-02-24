package com.giro.testePratico.Services;

import com.giro.testePratico.Services.exceptions.EmailAlreadyExistsException;
import com.giro.testePratico.Services.exceptions.ObjectNotFoundException;
import com.giro.testePratico.dto.request.InvestorRequestDTO;
import com.giro.testePratico.dto.response.InvestorResponseDTO;
import com.giro.testePratico.dto.response.PaginatedResponseDTO;
import com.giro.testePratico.entities.Investor;
import com.giro.testePratico.repositories.InvestorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    public PaginatedResponseDTO<InvestorResponseDTO> getAllInvestors(Pageable pageable) {
        Page<Investor> page = investorRepository.findAll(pageable);

        List<InvestorResponseDTO> content = page.getContent().stream()
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
