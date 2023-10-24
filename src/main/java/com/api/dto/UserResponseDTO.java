package com.api.dto;

import com.api.model.User;

import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String username;
    private List<String> role;

    private UserResponseDTO(Long id, String name, String username, List<String> role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role;
    }

    public static UserResponseDTO turnForDTO(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getUsername(), user.getRole());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
