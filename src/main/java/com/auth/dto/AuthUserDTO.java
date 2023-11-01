package com.auth.dto;

import com.auth.model.Role;

import java.util.List;

public record AuthUserDTO(String username, String password, List<Role> role) { }
