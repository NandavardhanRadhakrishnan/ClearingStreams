package com.cs.ClearingStreams.rules;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.RuleResult;

public interface Rule {

    public RuleResult apply(CanonicalTransactionDto dto);

}
