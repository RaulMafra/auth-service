package com.api.security;

import com.api.model.User;
import com.api.repository.UserRepository;
import com.api.security.config.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserAthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Verifica se o endpoint requer autenticação antes de processar a requisição
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request); //Recupera o token do header Authorization da requisição
            if (token != null) {
                String subject = jwtTokenService.verificationToken(token); //Retorna o assunto(username) do token verificado.
                User user = userRepository.findByUsername(subject).get(); //Busca o usuário pelo username
                UserDetailsImpl userDetails = new UserDetailsImpl(user); //Cria um UserDetails com base no usuário encontrado
                //Inicializa um objeto de autenticação com o username e a(s) role(s) do usuário encontrado
                Authentication authenticaton =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                //Define o objeto de autenticação no contexto de segurança do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authenticaton);
            } else {
                throw new RuntimeException("The token is missing.");
            }
        }
        filterChain.doFilter(request, response); //Continua o processamento da requisição
    }

    //Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    //Verifica se o recurso atual é privado, isto é, verifica se requer autenticação antes de processar a requisição.
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return List.of(SecurityConfiguration.RESOURCES_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}
