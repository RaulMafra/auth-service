package com.auth.service;

import com.auth.dto.*;
import com.auth.handler.MessagesExceptions;
import com.auth.model.Role;
import com.auth.model.User;
import com.auth.repository.UserRepository;
import com.auth.security.JwtTokenService;
import com.auth.security.UserDetailsImpl;
import com.auth.security.config.SecurityConfiguration;
import com.auth.util.FieldsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        FieldsValidator.checkNullFieldsAuthUserDTO(authUserDTO);
        UsernamePasswordAuthenticationToken usernamePassword = new
                UsernamePasswordAuthenticationToken(authUserDTO.username(), authUserDTO.password());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        return new RetriveJWTTokenDTO(jwtTokenService.generationToken((UserDetailsImpl) authentication.getPrincipal()));
    }

    public void RegisterUser(List<RegisterUserDTO> registerUserDTO) {
        FieldsValidator.checkNullFieldsRegisterUserDTO(registerUserDTO);
        registerUserDTO.stream().forEach(user -> {
            User newUser = new User(user.name(), user.username(), securityConfiguration.passwordEncoder().encode(user.password())
                    ,List.of(new Role(user.role())));
            userRepository.saveAndFlush(newUser);
        });
    }

    public void delete(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    public void update(UpdateUserDTO updateUser, String username) {
        FieldsValidator.checkNullFieldsUpdateUserDTO(updateUser);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        userRepository.updateUser(user.getId(), updateUser.name(), updateUser.username(), securityConfiguration.passwordEncoder().encode(updateUser.password()));
    }

    public ListUser myUser(String username) {
        FieldsValidator.checkNullFieldMyUser(username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        return new ListUser(user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getRole().get(0).getName());
    }

    public List<ListUser> listAllUsers() {
        Comparator<? super ListUser> comparator = Comparator.comparingLong(ListUser::id);

        List<User> users = userRepository.findAll();
        if (Optional.of(users).get().isEmpty()) {
            throw new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND);
        }
        return users.stream().map(u -> new ListUser(u.getId(), u.getName(), u.getUsername(), u.getPassword(), u.getRole().get(0).getName()))
                .sorted(comparator).collect(Collectors.toList());

    }
}