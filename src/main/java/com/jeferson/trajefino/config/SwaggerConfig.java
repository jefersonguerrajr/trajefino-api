package com.jeferson.trajefino.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Demo API",
                version = "v1",
                description = "Documentação da API Trajefino",
                termsOfService = "https://example.com/terms",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "Jeferson",
                        email = "jefersonguerrajr@live.com"
                ),
                license = @io.swagger.v3.oas.annotations.info.License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        )
)
public class SwaggerConfig {

}
