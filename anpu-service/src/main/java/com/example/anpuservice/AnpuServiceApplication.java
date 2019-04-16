package com.example.anpuservice;

import com.example.anpuservice.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class AnpuServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnpuServiceApplication.class, args);
    }

}
