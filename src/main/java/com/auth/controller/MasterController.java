package com.auth.controller;

import com.auth.dto.ListUser;
import com.auth.dto.ResponseDTO;
import com.auth.dto.UpdateUserDTO;
import com.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restservice/v1/master/users")
public class MasterController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<ListUser>> listUsers() {
        List<ListUser> listUsers = userService.listAllUsers();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
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
