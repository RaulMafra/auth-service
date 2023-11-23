package com.auth.security;

import com.auth.handler.exceptions.BusinessException;
import com.auth.handler.MessagesExceptions;
import com.auth.repository.UserRepository;
import com.auth.security.config.SecurityConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);
            if (token != null) {
                String subject = jwtTokenService.verificationToken(token);
                UserDetailsImpl userDetails = new UserDetailsImpl(userRepository.findByUsername(subject).
                        orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND)));
                Authentication authenticaton =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticaton);
            } else {
                throw new BusinessException(MessagesExceptions.INCORRECT_TOKEN_OR_LOST);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        List<String> accessAllowed = Arrays.asList(SecurityConfiguration.RESOURCES_WITH_AUTHENTICATION_NOT_REQUIRED);
        if(!requestURI.contains("/favicon.ico")) return !accessAllowed.stream().anyMatch(requestURI::startsWith);
        else return false;
    }

}
