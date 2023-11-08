/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author Waldir.Valle
 */
@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "Bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER,
    paramName = "Authorization"
)
public class OpenAPIConfig {

    @Value("${desarrollo.openapi.dev-url}")
    private String devUrl;

    @Value("${produccion.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server().url(devUrl).description("Server URL in Development environment");
        Server prodServer = new Server().url(prodUrl).description("Server URL in Production environment");

        Info info = new Info()
            .title("Examen CRUD SEEKOP")
            .version("1.0")
            .contact(new Contact()
                .email("itzaet.leonel.r@gmail.com")
                .name("Itzae Leonel de la Rosa")
                .url("")
            )
            .description("Examen para aprobar.")
            .termsOfService("")
            .license(new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/")
            );

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}