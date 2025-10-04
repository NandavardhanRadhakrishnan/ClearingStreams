package com.cs.ClearingStreams.rules;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.CanonicalResponseDto;
import com.cs.ClearingStreams.repositories.AmountLimitRepository;
import com.cs.ClearingStreams.repositories.ExchangeRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class AmountLimitRule implements Rule {

    private AmountLimitRepository amountLimitRepository;
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public String getName() {
        return "AMOUNT_LIMIT";
    }

    @Override
    public CanonicalResponseDto<CanonicalTransactionDto> apply(CanonicalTransactionDto dto) {
        BigDecimal limitInCurrency = amountLimitRepository.findAmountLimitEntitiesByTransactionType(dto.getType()).getAmountLimit()
                .multiply(exchangeRateRepository.findExchangeRateEntityByCurrencyCode(dto.getCurrency().toString()));
        if (dto.getAmount().compareTo(limitInCurrency) > 0) {
            String message = dto.getType().toString() + " does not allow more than " + limitInCurrency;
            return CanonicalResponseDto.fail(dto, getName(), message, CanonicalTransactionDto.Fields.amount);
        }

        return CanonicalResponseDto.pass();
    }
}
