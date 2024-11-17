package com.khu.yaong.global.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    private final String version = "1.0.0";

    @Bean
    public OpenAPI openAPI() {

        String jwtSchemeName = "accessToken";
        //String refreshToken = "refreshToken";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(jwtSchemeName);

        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .components(new Components())
                .info(swaggerInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }


    private Info swaggerInfo() {
        return new Info()
                .version("v" + version)
                .title("YAONG API")
                .description("YAONG 서버 API 문서입니다.");
    }
}