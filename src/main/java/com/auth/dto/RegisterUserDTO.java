package com.auth.dto;

import com.auth.model.RoleName;

public record RegisterUserDTO(String name, String username, String password, RoleName role) { }
