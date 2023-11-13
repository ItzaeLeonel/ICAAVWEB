package com.sistemasmig.icaavWeb.accounting.configuration;

import java.util.EnumSet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.sistemasmig.icaavWeb.accounting.services.JwtService;
import com.sistemasmig.icaavWeb.accounting.utils.JwtTokenFilter;

import jakarta.servlet.DispatcherType;

@Configuration
public class TokenConfig {

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> customFilter(JwtService jwtService) {
        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtTokenFilter(jwtService));

        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
        registrationBean.setAsyncSupported(true);
        registrationBean.setMatchAfter(true); 

        return registrationBean;
    }
}