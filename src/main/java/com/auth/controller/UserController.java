package com.auth.controller;

import com.auth.dto.AuthUserDTO;
import com.auth.dto.RegisterUserDTO;
import com.auth.dto.RetriveJWTTokenDTO;
import com.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody RegisterUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return new ResponseEntity<>("User created: Ok", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<RetriveJWTTokenDTO> authenticateUser(@RequestBody AuthUserDTO authUserDTO) {
        RetriveJWTTokenDTO token = userService.authenticateUser(authUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/myUser")
    public ResponseEntity<String> commonUser() {
        return new ResponseEntity<>("Usuário comum autorizado!", HttpStatus.OK);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<String> admin() {
        return new ResponseEntity<>("Administrador autorizado!", HttpStatus.OK);
    }

}
