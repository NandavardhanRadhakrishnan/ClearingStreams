package com.cs.ClearingStreams.util.kafka;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaUtil {

    private final KafkaTemplate<String, CanonicalTransactionDto> kafkaTemplate;

    private final KafkaTemplate<String, String > loggerKafkaTemplate;

    public void publish(String topic, CanonicalTransactionDto dto) {
        kafkaTemplate.send(topic, dto.getId(), dto);
    }

    public void publishLog(String topic, String message) {
        loggerKafkaTemplate.send(topic, message);
    }
}
