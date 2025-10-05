package com.cs.ClearingStreams.services.rules;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.dtos.CanonicalResponseDto;

public interface Rule {

    String getName();

    CanonicalResponseDto<CanonicalTransactionDto> apply(CanonicalTransactionDto dto);
}
