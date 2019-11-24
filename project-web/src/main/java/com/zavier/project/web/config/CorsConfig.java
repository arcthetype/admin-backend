package com.zavier.project.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET", "OPTIONS", "PUT", "DELETE")
                        .allowedHeaders("access-control-allow-origin", "access-control-allow-headers", "authorization", "x-requested-with", "content-type")
                        .exposedHeaders("Content-Length", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Content-Type")
                        .allowCredentials(true)
                        // 20天（1728000秒）
                        .maxAge(172800);
            }
        };
    }
}
