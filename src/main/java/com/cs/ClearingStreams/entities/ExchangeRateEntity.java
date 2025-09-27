package com.cs.ClearingStreams.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rule_exchange_rate")
public class ExchangeRateEntity {

    @Id
    private String currencyCode;
    private BigDecimal rate;
}
