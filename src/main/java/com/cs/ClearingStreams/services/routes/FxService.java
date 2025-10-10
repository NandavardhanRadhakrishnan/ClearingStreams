package com.cs.ClearingStreams.services.routes;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.cs.ClearingStreams.repositories.ExchangeRateRepository;
import com.cs.ClearingStreams.services.RouteEngineService;
import com.cs.ClearingStreams.util.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
@RequiredArgsConstructor
public class FxService extends AbstractMutationService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final KafkaTopics kafkaTopics;
    private final RouteEngineService routeEngineService;

    @Value("${base.currency}")
    private String baseCurrency;

    @Override
    public String getTopic() {
        return "${kafka.topics.fxService}";
    }


    @Override
    public void apply(CanonicalTransactionDto dto) {
        dto.setAmount(dto.getAmount().multiply(exchangeRateRepository.findExchangeRateEntityByCurrencyCode(dto.getCurrency().toString())));
        dto.setCurrency(Currency.getInstance(baseCurrency));
        routeEngineService.publishToPostValidation(dto);
    }



}
