package com.cs.ClearingStreams.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Builder
@Data
public class AccountDto {

    public String account;

    public Locale.IsoCountryCode country;

}
