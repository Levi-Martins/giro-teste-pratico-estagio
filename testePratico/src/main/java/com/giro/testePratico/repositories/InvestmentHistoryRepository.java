package com.giro.testePratico.repositories;

import com.giro.testePratico.entities.InvestmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentHistoryRepository extends JpaRepository<InvestmentHistory, Long> {
}
