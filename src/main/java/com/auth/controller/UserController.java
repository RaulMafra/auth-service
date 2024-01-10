package com.auth.controller;

import com.auth.dto.ListUserDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/restservice/v1/users")
@SecurityRequirement(name = "Bearer authorization")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get my user informations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ListUserDTO.class))}, description = "Informations getted successfully"),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json")}, description = "Informations not matchers or there is some incorrect field"),
            @ApiResponse(responseCode = "403", content = {@Content(mediaType = "application/json")}, description = "The request was denied")
    })
    @GetMapping("/{username}")
    public ResponseEntity<ListUserDTO> myUser(@PathVariable String username) {
        ListUserDTO listUserDTO = userService.myUser(username, getBearerTokenAuthorization());
        return new ResponseEntity<>(listUserDTO, HttpStatus.OK);
    }

    public String getBearerTokenAuthorization() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization").replace("Bearer ", "");

    }

}
