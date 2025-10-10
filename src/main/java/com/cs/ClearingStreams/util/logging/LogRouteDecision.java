package com.cs.ClearingStreams.util.logging;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.entities.RouteMasterEntity;
import com.cs.ClearingStreams.util.kafka.KafkaTopics;
import com.cs.ClearingStreams.util.kafka.KafkaUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LogRouteDecision {

    private final KafkaUtil kafkaUtil;
    private final KafkaTopics kafkaTopics;
    private final ObjectMapper objectMapper;

    public void log(CanonicalTransactionDto dto, List<RouteMasterEntity> eligibleRoutes) throws JsonProcessingException {
        log(dto, eligibleRoutes, null);
    }

    public void log(CanonicalTransactionDto dto, List<RouteMasterEntity> eligibleRoutes, String failure) throws JsonProcessingException {
        Map<String, Object> routeEvent = new HashMap<>();
        routeEvent.put("eventType", "route_decision");
        routeEvent.put("id",dto.getId());
        routeEvent.put("timestamp", Instant.now());
        routeEvent.put("selectedRoutes",
                eligibleRoutes.stream().map(routeMasterEntity ->
                                Map.of(
                                        "name",routeMasterEntity.getName(),
                                        "topic", routeMasterEntity.getTopic(),
                                        "criteria", routeMasterEntity.getCriteria(),
                                        "isMutating",routeMasterEntity.isMutating()
                                )
                        )
        );
        if (Objects.isNull(failure) || failure.isBlank()){
            routeEvent.put("failure",failure);
        }

        String json = objectMapper.writeValueAsString(routeEvent);
        kafkaUtil.publishLog(kafkaTopics.getTopics().get("logRoute"), json);

    }

}
