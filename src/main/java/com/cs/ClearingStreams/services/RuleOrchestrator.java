package com.cs.ClearingStreams.services;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.CanonicalResponseDto;
import com.cs.ClearingStreams.rules.Rule;
import com.cs.ClearingStreams.rules.RuleFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RuleOrchestrator {

    private final RuleFactory ruleFactory;

    public List<CanonicalResponseDto<CanonicalTransactionDto>> validate(CanonicalTransactionDto dto) {

        List<CanonicalResponseDto<CanonicalTransactionDto>> results = new ArrayList<>();
        List<Rule> rules = ruleFactory.getRulesForType(dto.getType());

        for (Rule rule : rules) {
            results.add(rule.apply(dto));
        }

        return results;
    }

}
