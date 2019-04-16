package com.example.anpuservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final CorsConfiguration cors = new CorsConfiguration();

    public CorsConfiguration getCors() {
        return cors;
    }
}
