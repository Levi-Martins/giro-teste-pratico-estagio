package com.giro.testePratico.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvestmentHistory  extends BaseEntity {

    private float initialAmount;
    private int months;
    private float interestRate;
    private float finalAmount;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = false)
    private Investor investor;

}
