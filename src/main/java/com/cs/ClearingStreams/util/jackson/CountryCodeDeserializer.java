package com.cs.ClearingStreams.util.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class CountryCodeDeserializer extends JsonDeserializer<Locale> {

    @Override
    public Locale deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String code = jsonParser.getText();
        if (!Arrays.asList(Locale.getISOCountries()).contains(code)) {
            throw deserializationContext.weirdStringException(code, Locale.class, "Invalid country code");
        }
        return new Locale("", code);
    }
}
