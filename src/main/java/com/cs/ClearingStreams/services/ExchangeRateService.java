package com.cs.ClearingStreams.services;

import com.cs.ClearingStreams.entities.ExchangeRateEntity;
import com.cs.ClearingStreams.repositories.ExchangeRateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

//    TODO: replace with a proper api client
    private final RestTemplate restTemplate = new RestTemplate();
    private final ExchangeRateRepository exchangeRateRepository;

    @Value("${exchange.endpoint}")
    private String endpoint;

    public void hydrate(){
        JsonNode response = restTemplate.getForObject(endpoint, JsonNode.class);
        List<ExchangeRateEntity> exchangeRateEntities = new ArrayList<>();

        response.get("conversion_rates").forEachEntry(
                (currencyCode, rate) -> {
                    exchangeRateEntities.add(new ExchangeRateEntity(currencyCode,BigDecimal.valueOf(rate.asDouble())));
                }
        );

        exchangeRateRepository.saveAll(exchangeRateEntities);
    }
}
