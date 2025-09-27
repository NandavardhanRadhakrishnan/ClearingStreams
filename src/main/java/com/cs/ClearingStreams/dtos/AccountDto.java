package com.cs.ClearingStreams.dtos;

import com.cs.ClearingStreams.util.jackson.CountryCodeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Builder
@Data
public class AccountDto {

    public String account;

    @JsonDeserialize(using = CountryCodeDeserializer.class)
    public Locale country;

}
