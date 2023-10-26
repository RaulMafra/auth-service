package com.api.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public static final String[] RESOURCES_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "user/login", //Realizar login
            "user/register" //Realizar cadastro
    };
}
