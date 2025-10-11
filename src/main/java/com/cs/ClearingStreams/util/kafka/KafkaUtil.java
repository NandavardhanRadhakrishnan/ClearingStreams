package com.cs.ClearingStreams.util.kafka;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KafkaUtil {

    private final KafkaTemplate<String, CanonicalTransactionDto> kafkaTemplate;

    private final KafkaTemplate<String, Map<String, Object>> loggerKafkaTemplate;

    public void publish(String topic, CanonicalTransactionDto dto) {
        kafkaTemplate.send(topic, dto.getId(), dto);
    }

    public void publishLog(String topic, Map<String, Object> message) {
        loggerKafkaTemplate.send(topic, message);
    }
}
