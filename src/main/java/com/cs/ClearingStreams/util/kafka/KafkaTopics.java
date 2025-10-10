package com.cs.ClearingStreams.util.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "kafka")
public class KafkaTopics {
    private Map<String, String> topics;
}
