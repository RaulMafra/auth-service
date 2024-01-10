package com.auth.dto;

import com.auth.model.RoleName;

public record ListUser(Long id, String name, String username, String password, RoleName roleName) {}
