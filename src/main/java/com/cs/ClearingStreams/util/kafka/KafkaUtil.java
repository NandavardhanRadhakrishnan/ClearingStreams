package com.cs.ClearingStreams.util.kafka;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaUtil {

    private final KafkaTemplate<String, CanonicalTransactionDto> kafkaTemplate;

    public void publish(String topic, CanonicalTransactionDto dto) {
        kafkaTemplate.send(topic, dto.getId(), dto);
    }
}
