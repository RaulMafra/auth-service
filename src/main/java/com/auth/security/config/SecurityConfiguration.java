package com.auth.security.config;

import com.auth.security.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] RESOURCES_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/user/login",
            "/user/register",
            "/h2-console"
    };

    public static final String[] RESOURCE_ADMINISTRATOR = {
            "/user/listUsers"
    };

    public static final String[] RESOURCE_USER = {
            "/user/myUser"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable() //Desativa a proteção conta CSRF
                .headers().frameOptions().disable().and() //Desabilita o frame para exibição do h2 console
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Configura a política de sessão como stateless
                .and().authorizeHttpRequests() //Habilita requisições HTTP
                .requestMatchers(RESOURCES_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                .requestMatchers(RESOURCE_ADMINISTRATOR).hasRole("ADMINISTRATOR")
                .requestMatchers(RESOURCE_USER).hasRole("USER")
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                .anyRequest().denyAll() //Bloqueia os endpoints para qualquer outra role
                .and() //Adiciona o filtro de autenticação do usuário criado anteriormente antes do filtro padrão do Spring Security
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
