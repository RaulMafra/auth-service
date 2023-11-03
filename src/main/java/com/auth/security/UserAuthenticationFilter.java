package com.auth.security;

import com.auth.model.User;
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
                throw new RuntimeException("The token was missing.");
            }
        }
        filterChain.doFilter(request, response); //Continua o processamento da requisição
    }

    //Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization"); //Acessa o valor "Authorization" do header da requisição enviada pelo client
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    //Verifica se o recurso atual é privado, isto é, verifica se requer autenticação antes de processar a requisição.
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI(); //Obtém o endpoint chamado na requisição pelo client
        List<String> accessAllowed = Arrays.asList(SecurityConfiguration.RESOURCES_WITH_AUTHENTICATION_NOT_REQUIRED);
        return requestURI.startsWith(String.valueOf(accessAllowed));

    }

}
