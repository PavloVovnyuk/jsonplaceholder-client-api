package com.pavlo.vovnyuk.jsonplaceholder.client.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavlo.vovnyuk.jsonplaceholder.client.errorhandler.RestTemplateErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new RestTemplateErrorHandler())
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
