package com.cs.ClearingStreams.dtos;

import com.cs.ClearingStreams.util.jackson.CountryCodeDeserializer;
import com.cs.ClearingStreams.util.jackson.CountryCodeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldNameConstants
public class AccountDto {

    public String account;

    @JsonDeserialize(using = CountryCodeDeserializer.class)
    @JsonSerialize(using = CountryCodeSerializer.class)
    public Locale country;

}
