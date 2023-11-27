package com.auth.service;

import com.auth.dto.AuthUserDTO;
import com.auth.dto.RegisterUserDTO;
import com.auth.dto.RetriveJWTTokenDTO;
import com.auth.dto.UpdateUserDTO;
import com.auth.handler.MessagesExceptions;
import com.auth.model.Role;
import com.auth.model.User;
import com.auth.repository.UserRepository;
import com.auth.security.JwtTokenService;
import com.auth.security.UserDetailsImpl;
import com.auth.security.config.SecurityConfiguration;
import com.auth.util.FieldsValidator;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.*;
import java.util.List;
import java.util.Properties;

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
        FieldsValidator.checkNullAuthUserDTO(authUserDTO);
        UsernamePasswordAuthenticationToken usernamePassword = new
                UsernamePasswordAuthenticationToken(authUserDTO.username(), authUserDTO.password());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        return new RetriveJWTTokenDTO(jwtTokenService.generationToken((UserDetailsImpl) authentication.getPrincipal()));
    }

    public void RegisterUser(RegisterUserDTO registerUserDTO) {
        FieldsValidator.checkNullRegisterUserDTO(registerUserDTO);
        User newUser = new User(registerUserDTO.name(), registerUserDTO.username(), securityConfiguration.passwordEncoder().encode(registerUserDTO.password())
                ,List.of(new Role(registerUserDTO.role())));
        userRepository.save(newUser);
    }

    public void delete(String username) {
       User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Transactional
    public void update(UpdateUserDTO updateUser, String username) {
        FieldsValidator.checkNullUpdateUserDTO(updateUser);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        userRepository.updateUser(user.getId(), updateUser.name(), updateUser.username(), securityConfiguration.passwordEncoder().encode(updateUser.password()));
    }
}