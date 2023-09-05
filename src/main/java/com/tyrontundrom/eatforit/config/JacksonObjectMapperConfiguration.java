package com.tyrontundrom.eatforit.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
class JacksonObjectMapperConfiguration {

    public void customize(ObjectMapper objectMapper) {
        objectMapper
                .configOverride(BigDecimal.class).setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.STRING));
    }
}
