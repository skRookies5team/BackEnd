package com.AIagnet.agent.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * FastAPI 서버 헬스체크 Indicator.
 * /actuator/health 엔드포인트에서 FastAPI 상태를 확인할 수 있습니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FastApiHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;

    @Value("${ai.fastapi.url}")
    private String fastApiUrl;

    @Override
    public Health health() {
        try {
            // FastAPI의 /health 또는 /ping 엔드포인트 호출
            String healthUrl = fastApiUrl + "/health";
            
            log.debug("FastAPI 헬스체크 요청: {}", healthUrl);
            
            Map<String, Object> response = restTemplate.getForObject(healthUrl, Map.class);
            
            if (response != null && (response.containsKey("status") || response.containsKey("message"))) {
                log.debug("FastAPI 헬스체크 성공: {}", response);
                return Health.up()
                        .withDetail("fastapi_url", fastApiUrl)
                        .withDetail("status", "UP")
                        .withDetails(response)
                        .build();
            } else {
                // 응답은 있지만 예상 형식이 아님
                log.warn("FastAPI 헬스체크 응답 형식 이상: {}", response);
                return Health.up()
                        .withDetail("fastapi_url", fastApiUrl)
                        .withDetail("status", "UP")
                        .withDetail("warning", "Unexpected response format")
                        .build();
            }
            
        } catch (RestClientException e) {
            log.error("FastAPI 헬스체크 실패: {}", e.getMessage());
            return Health.down()
                    .withDetail("fastapi_url", fastApiUrl)
                    .withDetail("status", "DOWN")
                    .withDetail("error", e.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("FastAPI 헬스체크 예외 발생: {}", e.getMessage(), e);
            return Health.down()
                    .withDetail("fastapi_url", fastApiUrl)
                    .withDetail("status", "DOWN")
                    .withDetail("error", e.getClass().getSimpleName() + ": " + e.getMessage())
                    .build();
        }
    }
}

