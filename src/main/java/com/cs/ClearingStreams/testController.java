package com.cs.ClearingStreams;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.RuleResult;
import com.cs.ClearingStreams.rules.SanctionRule;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class testController {

    private final SanctionRule sanctionRule;

    @GetMapping("/CTD")
        public ResponseEntity<RuleResult<String>> get(
                @RequestBody CanonicalTransactionDto dto
    ) {
        return ResponseEntity.ok(sanctionRule.apply(dto));
    }
}
