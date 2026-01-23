package com.dimiya.studentinquiry.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI studentInquiryOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Inquiry Service API")
                        .description("Spring Boot REST API for managing student inquiries and lecturer responses")
                        .version("v1"));
    }
}

