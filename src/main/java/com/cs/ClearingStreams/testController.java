package com.cs.ClearingStreams;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.RuleResult;
import com.cs.ClearingStreams.rules.AmountLimitRule;
import com.cs.ClearingStreams.rules.SanctionRule;
import com.cs.ClearingStreams.services.ExchangeRateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class testController {

    private final SanctionRule sanctionRule;
    private final AmountLimitRule amountLimitRule;
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/CTD")
        public ResponseEntity<RuleResult<String>> get(
                @RequestBody CanonicalTransactionDto dto
    ) {
        return ResponseEntity.ok(amountLimitRule.apply(dto));
    }

    @GetMapping("/hydrateRates") public String sync(){
        exchangeRateService.hydrate();
        return "done";
    }
}
