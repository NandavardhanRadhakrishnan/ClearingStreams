package com.cs.ClearingStreams.repositories;

import com.cs.ClearingStreams.constants.enums.TransactionType;
import com.cs.ClearingStreams.entities.AmountLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountLimitRepository extends JpaRepository<AmountLimitEntity, Long> {

    AmountLimitEntity findAmountLimitEntitiesByTransactionType(TransactionType transactionType);
}
