package com.cs.ClearingStreams;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.CanonicalResponseDto;
import com.cs.ClearingStreams.repositories.RuleMasterRepository;
import com.cs.ClearingStreams.rules.AmountLimitRule;
import com.cs.ClearingStreams.rules.LuhnCheckRule;
import com.cs.ClearingStreams.rules.SanctionRule;
import com.cs.ClearingStreams.services.ExchangeRateService;
import com.cs.ClearingStreams.services.RuleOrchestrator;
import com.cs.ClearingStreams.util.kafka.KafkaUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor
public class testController {

    private final SanctionRule sanctionRule;
    private final AmountLimitRule amountLimitRule;
    private final ExchangeRateService exchangeRateService;
    private final LuhnCheckRule luhnCheckRule;
    private final RuleMasterRepository ruleMasterRepository;
    private final RuleOrchestrator ruleOrchestrator;
    private final KafkaUtil kafkaUtil;

    @GetMapping("/CTD")
    public ResponseEntity<CanonicalResponseDto<CanonicalTransactionDto>> get(
            @RequestBody CanonicalTransactionDto dto
    ) {
        return ResponseEntity.ok(amountLimitRule.apply(dto));
    }

    @GetMapping("/foo")
    public String foo() {
        kafkaUtil.publish("test", CanonicalTransactionDto.builder().id("test123").amount(BigDecimal.TEN).build());
        return "done";
    }

    @GetMapping("/hydrateRates")
    public String sync() {
        exchangeRateService.hydrate();
        return "done";
    }

    @PostMapping("/validate")
    public ResponseEntity<List<CanonicalResponseDto<CanonicalTransactionDto>>> validateTransaction(
            @RequestBody CanonicalTransactionDto transactionDto) {

        List<CanonicalResponseDto<CanonicalTransactionDto>> results = ruleOrchestrator.validate(transactionDto);
        return ResponseEntity.ok(results);
    }
}
