package com.auth.controller;

import com.auth.dto.ListUser;
import com.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/restservice/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<ListUser> myUser(@PathVariable String username) {
        ListUser listUser = userService.myUser(username, getBearerTokenAuthorization());
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }

    public String getBearerTokenAuthorization() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization").replace("Bearer ", "");

    }

}
