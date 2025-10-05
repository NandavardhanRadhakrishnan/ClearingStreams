package com.cs.ClearingStreams.services.routes;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.repositories.ExchangeRateRepository;
import com.cs.ClearingStreams.util.kafka.KafkaTopics;
import com.cs.ClearingStreams.util.kafka.KafkaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
@RequiredArgsConstructor
public class FxService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final KafkaUtil kafkaUtil;
    private final KafkaTopics kafkaTopics;

    @Value("${base.currency}")
    private String baseCurrency;


    @KafkaListener(
            topics = "${kafka.topic.fxService}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    private void apply(CanonicalTransactionDto dto){
        dto.setAmount(dto.getAmount().multiply(exchangeRateRepository.findExchangeRateEntityByCurrencyCode(dto.getCurrency().toString())));
        dto.setCurrency(Currency.getInstance(baseCurrency));

        kafkaUtil.publish(kafkaTopics.postValidation(), dto);
    }
}
