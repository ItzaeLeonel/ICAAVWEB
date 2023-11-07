/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author Waldir.Valle
 */
@OpenAPIDefinition
@Configuration
public class SpringDocConfig {
    
    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI().info(new Info().title("Sistemas MIG IcaavWeb Contabilidad").version("1.0.0").description("Sistemas MIG IcaavWeb Contabilidad API Documentation"));
    }
    
}
