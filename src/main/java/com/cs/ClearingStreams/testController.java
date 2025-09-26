package com.cs.ClearingStreams;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class testController {

    @GetMapping("/CTD")
        public ResponseEntity<java.lang.String> get(
            @RequestBody CanonicalTransactionDto transactionDto
            ) {
        return ResponseEntity.ok(transactionDto.toString());
    }
}
