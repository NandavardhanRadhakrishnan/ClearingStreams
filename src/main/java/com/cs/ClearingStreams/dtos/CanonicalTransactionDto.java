package com.cs.ClearingStreams.dtos;

import com.cs.ClearingStreams.constants.enums.TransactionType;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Currency;

@Builder
@Data
@FieldNameConstants
public class CanonicalTransactionDto {

    public String id;

    public TransactionType type;

    public String timestamp;

    public BigDecimal amount;

    public Currency currency;

    public AccountDto payer;

    public AccountDto payee;

    public String instrument;

    public JsonNode metadata;
}
