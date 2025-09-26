package com.cs.ClearingStreams.rules;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.RuleResult;
import com.cs.ClearingStreams.repositories.SanctionRepository;

import java.util.List;
import java.util.Set;

public class SanctionRule implements Rule{

    private SanctionRepository sanctionRepository;

    @Override
    public RuleResult apply(CanonicalTransactionDto dto) {
        List<String> sanctioned = sanctionRepository.findCountryCodesByActiveTrue();
        if(sanctioned.contains(dto.getPayee().getCountry().name()) || sanctioned.contains(dto.getPayer().getCountry().name())){
            return RuleResult.fail("Transaction involves sanctioned country");
        }

        return RuleResult.pass();
    }
}
