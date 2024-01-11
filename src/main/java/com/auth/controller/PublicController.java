package com.auth.controller;

import com.auth.dto.AuthUserDTO;
import com.auth.dto.RegisterUserDTO;
import com.auth.dto.ResponseDTO;
import com.auth.dto.RetriveJWTTokenDTO;
import com.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Register one or more users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegisterUserDTO.class))}, description = "Registered user"),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json")}, description = "User already existing or some incorret field")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody List<RegisterUserDTO> registerUserDTO) {
        userService.registerUser(registerUserDTO);
        return new ResponseEntity<>(new ResponseDTO("OK"), HttpStatus.CREATED);
    }

    @Operation(summary = "Authenticate to get access token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthUserDTO.class))}, description = "Authentication successful"),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json")}, description = "The is some incorret field"),
            @ApiResponse(responseCode = "401", content = {@Content(mediaType = "application/json")}, description = "Authentication unauthorized")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<RetriveJWTTokenDTO> authenticateUser(@RequestBody AuthUserDTO authUserDTO) {
        RetriveJWTTokenDTO token = userService.authenticateUser(authUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
