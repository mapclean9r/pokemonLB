package com.pokemonlb.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * For controller tests: applies the real {@link SecurityConfig} and sends the configured API key
 * with every MockMvc request, so tests can focus on the endpoint. {@code SecurityConfigTest}
 * imports {@link SecurityConfig} alone to test requests without the key.
 */
@TestConfiguration
@Import(SecurityConfig.class)
public class ApiKeyTestConfig {

    @Bean
    MockMvcBuilderCustomizer sendApiKey(@Value("${pokemonlb.api-key}") String apiKey) {
        return builder -> builder.defaultRequest(get("/").header(ApiKeyFilter.HEADER, apiKey));
    }
}
