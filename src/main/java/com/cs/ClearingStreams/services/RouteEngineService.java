package com.cs.ClearingStreams.services;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.entities.RouteMasterEntity;
import com.cs.ClearingStreams.exceptions.MultipleValidRoutes;
import com.cs.ClearingStreams.repositories.RouteMasterRepository;
import com.cs.ClearingStreams.services.routes.RouteCriteriaEvaluator;
import com.cs.ClearingStreams.util.kafka.KafkaTopics;
import com.cs.ClearingStreams.util.kafka.KafkaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteEngineService {

    private final KafkaUtil kafkaUtil;
    private final KafkaTopics kafkaTopics;
    private final RouteMasterRepository routeMasterRepository;
    private final RouteCriteriaEvaluator routeCriteriaEvaluator;

    public void publishToPostValidation(CanonicalTransactionDto canonicalTransactionDto){
        kafkaUtil.publish(kafkaTopics.getTopics().get("postValidation"), canonicalTransactionDto);
    }

    @KafkaListener(
            topics = "${kafka.topics.postValidation}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    private void route(CanonicalTransactionDto dto){
        List<RouteMasterEntity> routes = routeMasterRepository.findByActiveTrue();
        List<RouteMasterEntity> eligibleRoutes = routes
                .stream()
                .filter(
                        routeMasterEntity -> routeCriteriaEvaluator
                                .matches(dto,routeMasterEntity.getCriteria(),null)
                )
                .toList();
        if(eligibleRoutes.stream().filter(RouteMasterEntity::isMutating).count() > 1){
            throw new MultipleValidRoutes("multiple valid mutating routes found");
        }

        eligibleRoutes.forEach(routeMasterEntity -> kafkaUtil.publish(kafkaTopics.getTopics().get(routeMasterEntity.getTopic()),dto));
    }


}
