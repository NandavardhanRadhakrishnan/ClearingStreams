package com.cs.ClearingStreams.services.routes;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import org.springframework.kafka.annotation.KafkaListener;

public abstract class AbstractMutationService {

    public abstract String getTopic();

    public abstract void apply(CanonicalTransactionDto dto);


    @KafkaListener(
            topics =  "#{__listener.getTopic()}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(CanonicalTransactionDto dto){
        apply(dto);
    }
}
