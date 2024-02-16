package br.com.mateusgarcia.vagas_spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestão de Vagas")
                        .description("API responsável pela gestão de vagas")
                        .version("1")
                )
                .schemaRequirement("jwt_auth", createSecuritySchema());
    }

    private SecurityScheme createSecuritySchema() {
        return new SecurityScheme()
                .name("jwt_auth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    };
}
