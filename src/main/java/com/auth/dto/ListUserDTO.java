package com.auth.dto;

import com.auth.model.RoleName;

public record ListUserDTO(Long id, String name, String username, String password, RoleName roleName) {}
