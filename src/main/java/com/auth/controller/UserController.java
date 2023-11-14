package com.auth.controller;

import com.auth.dto.*;
import com.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseEntity<ResponsesDTO> createUser(@RequestBody RegisterUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return new ResponseEntity<>(new ResponsesDTO("OK"), HttpStatus.CREATED);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsesDTO> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(new ResponsesDTO("OK"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUser, @PathVariable Long id) {
        userService.update(updateUser, id);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

}
