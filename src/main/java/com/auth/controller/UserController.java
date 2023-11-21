package com.auth.controller;

import com.auth.dto.*;
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
    public ResponseEntity<ResponseDTO> createUser(@RequestBody RegisterUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return new ResponseEntity<>(new ResponseDTO("OK"), HttpStatus.CREATED);
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

    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String username){
        userService.delete(username);
        return new ResponseEntity<>(new ResponseDTO("OK"), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUser, @PathVariable String username) {
        userService.update(updateUser, username);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

}
