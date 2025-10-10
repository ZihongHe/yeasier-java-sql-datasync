package com.smarttoys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射 /api/images/** 到 classpath:/public/images/
        registry.addResourceHandler("/api/images/**")
                .addResourceLocations("classpath:/public/images/");
    }
}