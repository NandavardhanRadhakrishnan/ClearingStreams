package com.cs.ClearingStreams.constants.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {
    @Override
    public String convertToDatabaseColumn(TransactionType transactionType) {
        return transactionType == null ? null : transactionType.name();
    }

    @Override
    public TransactionType convertToEntityAttribute(String s) {
        return s == null ? null : TransactionType.valueOf(s);
    }
}
