package com.cs.ClearingStreams.rules;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.RuleResult;
import com.cs.ClearingStreams.repositories.SanctionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class SanctionRule implements Rule{

    private SanctionRepository sanctionRepository;

    @Override
    public RuleResult<String> apply(CanonicalTransactionDto dto) {
        List<String> sanctioned = sanctionRepository.findCountryCodesByActiveTrue();
        if(sanctioned.contains(dto.getPayer().getCountry().getCountry()) || sanctioned.contains(dto.getPayee().getCountry().getCountry())){
            return RuleResult.fail("Transaction involves sanctioned country");
        }

        return RuleResult.pass();
    }
}
