package com.auth.security;

import com.auth.handler.MessagesExceptions;
import com.auth.repository.UserRepository;
import com.auth.security.config.SecurityConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (hasAuthorization(request)) {
            String token = recoveryToken(request);
            if (token != null) {
                String subject = jwtTokenService.verificationToken(token);
                UserDetailsImpl userDetails = new UserDetailsImpl(userRepository.findByUsername(subject).
                        orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND)));
                Authentication authenticaton =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticaton);
            } else {
                throw new BadCredentialsException("The token is incorrect or lost");
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

    private boolean hasAuthorization(HttpServletRequest request) {
        for (String paths : SecurityConfiguration.RESOURCE_ADMINISTRATOR) {
            String replacedPath = paths.replace("/**", "");
            if (request.getRequestURI().contains(replacedPath)) return true;
        }
        if (request.getRequestURI().contains(SecurityConfiguration.RESOURCE_USER.replace("/**", ""))) return true;
        else return false;
    }

}
