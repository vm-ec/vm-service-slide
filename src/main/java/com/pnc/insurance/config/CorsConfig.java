package com.pnc.insurance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/api/**")
                        "http://localhost:3000",
                                "http://localhost:5173",
                                "http://localhost:5174",
                                "https://vm-service-slide-1.onrender.com"
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
            //cross gobally config
        };
    }
}