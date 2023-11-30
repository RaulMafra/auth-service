package com.auth.dto;

import com.auth.model.RoleName;

public record UpdateUserDTO(String name, String username, String password, RoleName role) { }
