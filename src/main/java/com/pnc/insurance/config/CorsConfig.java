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
                        .allowedOriginPatterns(
                                "http://localhost:3000",
                                "http://localhost:5173",
                                "http://localhost:5174",
                                "https://vm-service-slide-1.onrender.com",
                                "https://.*\\.onrender\\.com"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);

                // Allow CORS for auth endpoints as well
                registry.addMapping("/auth/**")
                        .allowedOriginPatterns(
                                "http://localhost:3000",
                                "http://localhost:5173",
                                "http://localhost:5174",
                                "https://vm-service-slide-1.onrender.com",
                                "https://.*\\.onrender\\.com"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);

                // Allow CORS for URLs parallel endpoints
                registry.addMapping("/urls-parallel/**")
                        .allowedOriginPatterns(
                                "http://localhost:3000",
                                "http://localhost:5173",
                                "http://localhost:5174",
                                "https://vm-service-slide-1.onrender.com",
                                "https://.*\\.onrender\\.com"
                        )
                        .allowedMethods("GET", "POST", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);

                // Allow CORS for dashboard endpoints
                registry.addMapping("/dashboard/**")
                        .allowedOriginPatterns(
                                "http://localhost:3000",
                                "http://localhost:5173",
                                "http://localhost:5174",
                                "https://vm-service-slide-1.onrender.com",
                                "https://.*\\.onrender\\.com"
                        )
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);

                // Allow CORS for status endpoints
                registry.addMapping("/status/**")
                        .allowedOriginPatterns(
                                "http://localhost:3000",
                                "http://localhost:5173",
                                "http://localhost:5174",
                                "https://vm-service-slide-1.onrender.com",
                                "https://.*\\.onrender\\.com"
                        )
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}