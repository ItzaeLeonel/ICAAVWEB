package com.sistemasmig.icaavWeb.accounting.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sistemasmig.icaavWeb.accounting.services.JwtService;
import com.sistemasmig.icaavWeb.accounting.utils.JwtTokenFilter;

@Configuration
public class TokenConfig {
    @Bean
    public FilterRegistrationBean<JwtTokenFilter> customFilter(JwtService jwtService) {
        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtTokenFilter(jwtService));
        registrationBean.addUrlPatterns("/api/secure-endpoint"); 
        return registrationBean;
    }
}
