package com.cs.ClearingStreams.repositories;

import com.cs.ClearingStreams.entities.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, String> {

    @Query(value = "select rate from ExchangeRateEntity where currencyCode like :currencyCode")
    BigDecimal findExchangeRateEntityByCurrencyCode(String currencyCode);
}
