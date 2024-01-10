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
            "/restservice/v1/sign-up",
            "/restservice/v1/sign-in",
    };

    public static final String[] RESOURCES_OPEN_API = {
                "/favicon/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security"
    };

    public static final String H2_DATABASE = "/h2-console";


    public static final String[] RESOURCE_ADMINISTRATOR = {"/restservice/v1/master/users","/restservice/v1/master/users/**"};


    public static final String RESOURCE_USER = "/restservice/v1/users/**";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable().headers()
                .frameOptions().disable().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(RESOURCES_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(RESOURCE_ADMINISTRATOR).hasRole("ADMINISTRATOR")
                        .requestMatchers(RESOURCE_USER).hasRole("USER")
                        .requestMatchers(H2_DATABASE).permitAll()
                        .requestMatchers(RESOURCES_OPEN_API).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .anyRequest().denyAll())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
            Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
