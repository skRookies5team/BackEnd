package com.AIagnet.agent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * OpenAPI (Swagger) 설정.
 * Spring Boot API 문서를 제공합니다.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI unifiedOpenAPI() {
        String description = "AI Agent API 문서\n\n" +
                "이 문서는 Spring Boot 백엔드의 모든 API를 제공합니다.\n\n" +
                "주요 기능:\n" +
                "- 일정 관리: 일정 조회, 생성, 취소\n" +
                "- 출장 관리: 출장 조회, 생성, 삭제\n" +
                "- 연차 관리: 연차 잔여일 조회, 신청\n" +
                "- 회의실 관리: 회의실 조회, 예약, 추천\n" +
                "- 결재 관리: 결재 문서 조회, 생성\n" +
                "- 채용 관리: 채용 지원서 조회\n" +
                "- 매출 관리: 회사 매출 조회, 분석\n" +
                "- 지출 관리: 지출 거래 조회\n" +
                "- 모바일 기기: 모바일 기기 정보 조회\n" +
                "- 활동기준원가: 원가 분석";

        Info info = new Info();
        info.setTitle("AI Agent API 문서");
        info.setDescription(description);
        info.setVersion("1.0.0");
        
        Contact contact = new Contact();
        contact.setName("AI Agent Team");
        contact.setEmail("support@aiagent.com");
        info.setContact(contact);
        
        License license = new License();
        license.setName("Apache 2.0");
        license.setUrl("https://www.apache.org/licenses/LICENSE-2.0.html");
        info.setLicense(license);

        List<Server> servers = new ArrayList<>();
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Spring Boot 서버 (개발 환경)");
        servers.add(devServer);
        
        Server prodServer = new Server();
        prodServer.setUrl("https://api.aiagent.com");
        prodServer.setDescription("프로덕션 서버");
        servers.add(prodServer);

        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
        openAPI.setServers(servers);
        
        return openAPI;
    }
}

