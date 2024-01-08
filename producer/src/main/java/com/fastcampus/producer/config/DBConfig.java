package com.fastcampus.producer.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
