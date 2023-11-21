package com.auth.service;

import com.auth.dto.AuthUserDTO;
import com.auth.dto.RegisterUserDTO;
import com.auth.dto.RetriveJWTTokenDTO;
import com.auth.dto.UpdateUserDTO;
import com.auth.handler.BusinessException;
import com.auth.handler.MessagesExceptions;
import com.auth.model.Role;
import com.auth.model.RoleName;
import com.auth.model.User;
import com.auth.repository.UserRepository;
import com.auth.security.JwtTokenService;
import com.auth.security.UserDetailsImpl;
import com.auth.security.config.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RetriveJWTTokenDTO authenticateUser(AuthUserDTO authUserDTO) {
        if(Stream.of(authUserDTO.username(), authUserDTO.password()).anyMatch(Objects::isNull)){
            throw new BusinessException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
        }
        UsernamePasswordAuthenticationToken usernamePassword = new
                UsernamePasswordAuthenticationToken(authUserDTO.username(), authUserDTO.password());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        return new RetriveJWTTokenDTO(jwtTokenService.generationToken((UserDetailsImpl) authentication.getPrincipal()));
    }

    public void createUser(RegisterUserDTO createUserDTO) {
        if(Stream.of(createUserDTO.name(), createUserDTO.username(), createUserDTO.password(), createUserDTO.role()).anyMatch(Objects::isNull)){
            throw new BusinessException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
        }
        if(!Arrays.stream(RoleName.values()).anyMatch(role -> role.equals(createUserDTO.role()))){
            throw new RuntimeException("Role inválida");
        }
        User newUser = new User(createUserDTO.name(), createUserDTO.username(), securityConfiguration.passwordEncoder().encode(createUserDTO.password())
                ,List.of(new Role(createUserDTO.role())));
        userRepository.save(newUser);
    }

    public void delete(String username) {
       User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Transactional
    public void update(UpdateUserDTO updateUser, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        userRepository.updateUser(user.getId(), updateUser.name(), updateUser.username(), securityConfiguration.passwordEncoder().encode(updateUser.password()));
    }
}