package com.AIagnet.agent.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * RestTemplate 설정 (Python AI 서버 호출용)
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .setConnectTimeout(Duration.ofSeconds(5))  // 연결 타임아웃 5초
                .setReadTimeout(Duration.ofSeconds(30))    // 읽기 타임아웃 30초
                .build();
    }
}