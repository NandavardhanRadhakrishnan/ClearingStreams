package com.cs.ClearingStreams.rules;

import com.cs.ClearingStreams.constants.enums.TransactionType;
import com.cs.ClearingStreams.repositories.RuleMasterRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RuleFactory {

    private final Map<String, Rule> ruleMap;
    private final RuleMasterRepository ruleMasterRepository;


    public RuleFactory(Map<String, Rule> ruleMap, RuleMasterRepository ruleMasterRepository) {
//        spring injects all beans of type Rule updating their name to match getName
        this.ruleMap = ruleMap.values().stream().collect(Collectors.toMap(Rule::getName, r -> r));
        this.ruleMasterRepository = ruleMasterRepository;
    }

    public Rule getRule(String ruleName) {
        Rule rule = ruleMap.get(ruleName);

        if (Objects.isNull(rule)) {
            throw new IllegalArgumentException("No rule found for: " + ruleName);
        }
        return rule;
    }

    public List<Rule> getRulesForType(TransactionType transactionType) {
        return ruleMasterRepository.findRuleMasterEntitiesByTypeOrderByPriority(transactionType)
                .stream()
                .map(ruleMasterEntity -> getRule(ruleMasterEntity.getRule()))
                .toList();
    }
}
