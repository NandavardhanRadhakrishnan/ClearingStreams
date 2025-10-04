package com.cs.ClearingStreams.rules;

import com.cs.ClearingStreams.dtos.AccountDto;
import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.CanonicalResponseDto;
import com.cs.ClearingStreams.repositories.SanctionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class SanctionRule implements Rule {

    private SanctionRepository sanctionRepository;

    @Override
    public String getName() {
        return "SANCTION";
    }

    @Override
    public CanonicalResponseDto<CanonicalTransactionDto> apply(CanonicalTransactionDto dto) {
        List<String> sanctioned = sanctionRepository.findCountryCodesByActiveTrue();
        List<CanonicalResponseDto.RuleFailure> failures = new ArrayList<>();

        if (sanctioned.contains(dto.getPayer().getCountry().getCountry())) {
            failures.add(new CanonicalResponseDto.RuleFailure(
                    getName(),
                    "Transaction involves sanctioned country",
                    CanonicalTransactionDto.Fields.payer,
                    AccountDto.Fields.country
            ));
        }

        if (sanctioned.contains(dto.getPayee().getCountry().getCountry())) {
            failures.add(new CanonicalResponseDto.RuleFailure(
                    getName(),
                    "Transaction involves sanctioned country",
                    CanonicalTransactionDto.Fields.payee,
                    AccountDto.Fields.country
            ));
        }

        return failures.isEmpty() ? CanonicalResponseDto.pass() : CanonicalResponseDto.fail(dto, failures);
    }
}
