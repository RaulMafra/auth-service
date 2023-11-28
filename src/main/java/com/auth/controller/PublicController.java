package com.auth.controller;

import com.auth.dto.AuthUserDTO;
import com.auth.dto.RegisterUserDTO;
import com.auth.dto.ResponseDTO;
import com.auth.dto.RetriveJWTTokenDTO;
import com.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restservice/v1")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody List<RegisterUserDTO> registerUserDTO) {
        userService.RegisterUser(registerUserDTO);
        return new ResponseEntity<>(new ResponseDTO("OK"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<RetriveJWTTokenDTO> authenticateUser(@RequestBody AuthUserDTO authUserDTO) {
        RetriveJWTTokenDTO token = userService.authenticateUser(authUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
