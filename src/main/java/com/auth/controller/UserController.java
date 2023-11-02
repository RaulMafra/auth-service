package com.api.controller;

import com.api.dto.UserRequestDTO;
import com.api.dto.UserResponseDTO;
import com.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO dto) {
        User userCreated = userService.saveUser(dto.turnForObject());
        return new ResponseEntity<>(UserResponseDTO.turnForDTO(userCreated), HttpStatus.CREATED);
    }

/*    @GetMapping("/{username}")
    public User getOne(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @GetMapping
    public String getUser() {
        return "Usuário";
    }

    @GetMapping("/{username}")
    public String getUserByUsername(@PathVariable String username) {
        return "Usuário " + username + " encontrado!";
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }


    @PutMapping()
    public void updateUser(@RequestBody() User user) {
        userRepository.save(user);
    }*/


}
