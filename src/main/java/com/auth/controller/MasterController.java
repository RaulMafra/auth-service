package com.auth.controller;

import com.auth.dto.ListUserDTO;
import com.auth.dto.ResponseDTO;
import com.auth.dto.UpdateUserDTO;
import com.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restservice/v1/master/users")
@SecurityRequirement(name = "Bearer authorization")
public class MasterController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ListUserDTO.class))}, description = "Users returned with successful"),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json")}, description = "There is some incorrect field"),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = "application/json")}, description = "The request was denied")
    })
    @GetMapping
    public ResponseEntity<List<ListUserDTO>> listUsers() {
        List<ListUserDTO> listUserDTOS = userService.listAllUsers();
        return new ResponseEntity<>(listUserDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Remove a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}, description = "Removal successful"),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = "application/json")}, description = "The request was denied"),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = "application/json")}, description = "User not found")
    })
    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String username){
        userService.delete(username);
        return new ResponseEntity<>(new ResponseDTO("OK"), HttpStatus.OK);
    }

    @Operation(summary = "Update a user informations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUserDTO.class))}, description = "Update successful"),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = "application/json")}, description = "The request was denied"),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json")}, description = "There is some incorrect field"),
            @ApiResponse(responseCode = "404", content = {@Content(mediaType = "application/json")}, description = "User not found"),
    })
    @PutMapping("/{username}")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UpdateUserDTO updateUser, @PathVariable String username) {
        userService.update(updateUser, username);
        return new ResponseEntity<>(new ResponseDTO("OK"), HttpStatus.OK);
    }

}
