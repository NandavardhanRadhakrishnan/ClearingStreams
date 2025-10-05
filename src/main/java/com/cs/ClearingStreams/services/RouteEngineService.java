package com.cs.ClearingStreams.services;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.util.kafka.KafkaTopics;
import com.cs.ClearingStreams.util.kafka.KafkaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteEngineService {

    private final KafkaUtil kafkaUtil;
    private final KafkaTopics kafkaTopics;

    public void publishToPostValidation(CanonicalTransactionDto canonicalTransactionDto){
        kafkaUtil.publish(kafkaTopics.fxService(), canonicalTransactionDto);
    }


}
