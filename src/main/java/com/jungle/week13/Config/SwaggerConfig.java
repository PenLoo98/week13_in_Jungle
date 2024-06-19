package com.jungle.week13.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // API 문서의 메타데이터를 가진 Info객체 생성 + (버전, 제목, 설명) 설정
        Info info = new Info()
                .version("1.0")
                .title("CRUD API")
                .description("CRUD API 명세서");
        return new OpenAPI().info(info);
    }
}