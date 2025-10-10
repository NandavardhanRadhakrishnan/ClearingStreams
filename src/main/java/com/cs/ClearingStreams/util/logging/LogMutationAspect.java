package com.cs.ClearingStreams.util.logging;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.util.kafka.KafkaTopics;
import com.cs.ClearingStreams.util.kafka.KafkaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@RequiredArgsConstructor
public class LogMutationAspect {

    private final ObjectMapper objectMapper;
    private final KafkaUtil kafkaUtil;
    private final KafkaTopics kafkaTopics;

    @Around("@annotation(LogMutation)")
    public Object logMutation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object[] args = proceedingJoinPoint.getArgs();

        CanonicalTransactionDto before = extractDto(args);

        Object result = proceedingJoinPoint.proceed();

        CanonicalTransactionDto after = extractDto(args);

        Map<String, Object> mutationEvent = new HashMap<>();

        mutationEvent.put("eventType", "mutation");
        mutationEvent.put("id",before.getId());
        mutationEvent.put("timestamp", Instant.now());
        mutationEvent.put("actor", proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName());
        mutationEvent.put("before", before);
        mutationEvent.put("after", after);

        String json = objectMapper.writeValueAsString(mutationEvent);
        kafkaUtil.publishLog(kafkaTopics.getTopics().get("logMutation"), json);

        return result;
    }

    private CanonicalTransactionDto extractDto(Object[] args) {
        if (args == null) return null;
        for (Object arg : args) {
            if (arg instanceof CanonicalTransactionDto dto) {
                try {
                    return objectMapper.readValue(objectMapper.writeValueAsString(dto), CanonicalTransactionDto.class);
                } catch (Exception e) {
                    return dto;
                }
            }
        }
        return null;
    }
}
