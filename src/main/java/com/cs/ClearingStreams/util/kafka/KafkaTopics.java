package com.cs.ClearingStreams.util.kafka;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.topic")
public record KafkaTopics(
        String postValidation,
        String fxService
) {
}
