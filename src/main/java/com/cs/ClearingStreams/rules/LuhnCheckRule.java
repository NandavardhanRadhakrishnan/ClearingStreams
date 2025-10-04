package com.cs.ClearingStreams.rules;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.AccountDto;
import com.cs.ClearingStreams.dtos.CanonicalResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LuhnCheckRule implements Rule {

    @Override
    public String getName() {
        return "LUHN_CHECK";
    }

    @Override
    public CanonicalResponseDto<CanonicalTransactionDto> apply(CanonicalTransactionDto dto) {
        List<CanonicalResponseDto.RuleFailure> failures = new ArrayList<>();

        if (!luhnCheck(dto.getPayer().getAccount())) {
            failures.add(new CanonicalResponseDto.RuleFailure(
                    getName(),
                    "Invalid account number",
                    CanonicalTransactionDto.Fields.payer,
                    AccountDto.Fields.account
            ));
        }

        if (!luhnCheck(dto.getPayee().getAccount())) {
            failures.add(new CanonicalResponseDto.RuleFailure(
                    getName(),
                    "Invalid account number",
                    CanonicalTransactionDto.Fields.payee,
                    AccountDto.Fields.account
            ));
        }

        return failures.isEmpty() ? CanonicalResponseDto.pass() : CanonicalResponseDto.fail(dto, failures);
    }

    public boolean luhnCheck(String accountNo) {
        int len = accountNo.length();
        int totalSum = 0;
        String accountNoReversed = new StringBuilder(accountNo).reverse().toString();
        for (int i = 0; i < len; i++) {
            int weight = (i % 2) + 1;
            int weightedValue = weight * (accountNoReversed.charAt(i) - '0');
            totalSum += weightedValue > 9 ? weightedValue - 9 : weightedValue;
        }
        System.out.println(totalSum);
        return totalSum % 10 == 0;
    }
}
