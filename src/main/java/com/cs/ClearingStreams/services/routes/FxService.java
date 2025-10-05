package com.cs.ClearingStreams.services.routes;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.repositories.ExchangeRateRepository;
import com.cs.ClearingStreams.services.RouteEngineService;
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
    private final RouteEngineService routeEngineService;

    @Value("${base.currency}")
    private String baseCurrency;


    @KafkaListener(
            topics = "${kafka.topic.topics.fxService}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    private void apply(CanonicalTransactionDto dto){
        dto.setAmount(dto.getAmount().multiply(exchangeRateRepository.findExchangeRateEntityByCurrencyCode(dto.getCurrency().toString())));
        dto.setCurrency(Currency.getInstance(baseCurrency));
        System.out.println(dto);
        routeEngineService.publishToPostValidation(dto);
    }
}
