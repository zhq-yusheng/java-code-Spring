package com.yu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.yu.config")
public class BeanConfig {
    @Bean
    public student getStudent() {
        return new student();
    }
}
