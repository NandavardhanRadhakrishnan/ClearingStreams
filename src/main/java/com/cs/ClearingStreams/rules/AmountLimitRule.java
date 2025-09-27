package com.cs.ClearingStreams.rules;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.RuleResult;
import com.cs.ClearingStreams.repositories.AmountLimitRepository;
import com.cs.ClearingStreams.repositories.ExchangeRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class AmountLimitRule implements Rule{

    private AmountLimitRepository amountLimitRepository;
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public RuleResult<String> apply(CanonicalTransactionDto dto) {
        BigDecimal limitInCurrency = amountLimitRepository.findAmountLimitEntitiesByTransactionType(dto.getType().toString()).getAmountLimit()
                .multiply(exchangeRateRepository.findExchangeRateEntityByCurrencyCode(dto.getCurrency().toString()));
        if(dto.getAmount().compareTo(limitInCurrency) > 0){
            return RuleResult.fail(dto.getType().toString() + " does not allow more than " + limitInCurrency);
        }

        return RuleResult.pass();
    }
}
