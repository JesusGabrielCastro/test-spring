package com.task.crud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Productos API")
                        .version("1.0")
                        .description("CRUD de productos con Spring Boot y PostgreSQL")
                        .contact(new Contact()
                                .name("Tu Nombre")
                                .email("tu@email.com")
                        )
                );
    }
}