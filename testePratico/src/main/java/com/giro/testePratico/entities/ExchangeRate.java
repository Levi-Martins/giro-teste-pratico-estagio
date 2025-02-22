package com.giro.testePratico.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExchangeRate extends BaseEntity {

    private LocalDateTime date;
    private float dailyVariantion;
    private float dailyRate;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
}
