package com.zhh.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


public class ProductServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
