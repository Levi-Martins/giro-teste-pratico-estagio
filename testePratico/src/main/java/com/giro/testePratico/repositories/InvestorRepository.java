package com.giro.testePratico.repositories;

import com.giro.testePratico.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    boolean existsByEmail(String email);
    Optional<Investor> findByEmail(String email);

}
