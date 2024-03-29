package com.auth.service;

import com.auth.dto.*;
import com.auth.emailservice.infra.ses.SesEmailSender;
import com.auth.emailservice.service.EmailSenderService;
import com.auth.handler.MessagesExceptions;
import com.auth.handler.exceptions.AuthException;
import com.auth.handler.exceptions.EmailServiceException;
import com.auth.model.Role;
import com.auth.model.User;
import com.auth.repository.RoleRepository;
import com.auth.repository.UserRepository;
import com.auth.security.JwtTokenService;
import com.auth.security.UserDetailsImpl;
import com.auth.security.config.SecurityConfiguration;
import com.auth.util.FieldsValidator;
import com.auth0.jwt.JWT;
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
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailSenderService emailSenderService;

    public RetriveJWTTokenDTO authenticateUser(AuthUserDTO authUserDTO) {
        FieldsValidator.checkNullFieldsAuthUserDTO(authUserDTO);
        UsernamePasswordAuthenticationToken usernamePassword = new
                UsernamePasswordAuthenticationToken(authUserDTO.username(), authUserDTO.password());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        return new RetriveJWTTokenDTO(jwtTokenService.generationToken((UserDetailsImpl) authentication.getPrincipal()));
    }

    public void registerUser(List<RegisterUserDTO> registerUserDTO) {
        FieldsValidator.checkFieldsRegisterUserDTO(registerUserDTO);
        registerUserDTO.stream().forEach(user -> {
            User newUser = new User(user.name(), user.username(), securityConfiguration.passwordEncoder().encode(user.password())
                    ,new Role(user.role()));
            userRepository.saveAndFlush(newUser);
        });
        try{
         emailSenderService.sendEmail(SesEmailSender.EMAIL, "Sign-up of user", "Another user has been registered!");
        } catch(EmailServiceException e){
            throw new EmailServiceException(MessagesExceptions.FAILURE_SEND_EMAIL);
        }
    }

    public void delete(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    public void update(UpdateUserDTO updateUser, String username) {
        FieldsValidator.checkNullFieldsUpdateUserDTO(updateUser);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        if(updateUser.role() == null){
         userRepository.updateUserOnly(user.getId(), updateUser.name(), updateUser.username(),
                 securityConfiguration.passwordEncoder().encode(updateUser.password()));
        }
        else {
            userRepository.updateUserOnly(user.getId(), updateUser.name(), updateUser.username(),
                    securityConfiguration.passwordEncoder().encode(updateUser.password()));
            roleRepository.updateRole(user.getId(), String.valueOf(updateUser.role()));
        }
    }

    public ListUserDTO myUser(String username, String token) {
        FieldsValidator.checkNullFieldMyUser(username);
        String bearer = JWT.decode(token).getSubject();
        if(!username.equals(bearer)){
            throw new AuthException(MessagesExceptions.CHECK_MATCH_GET_USER);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
        return new ListUserDTO(user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getRole().getName());
    }

    public List<ListUserDTO> listAllUsers() {
        Comparator<? super ListUserDTO> comparator = Comparator.comparingLong(ListUserDTO::id);

        List<User> users = userRepository.findAll();
        if (Optional.of(users).get().isEmpty()) {
            throw new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND);
        }
        return users.stream().map(u -> new ListUserDTO(u.getId(), u.getName(), u.getUsername(), u.getPassword(), u.getRole().getName()))
                .sorted(comparator).collect(Collectors.toList());

    }
}