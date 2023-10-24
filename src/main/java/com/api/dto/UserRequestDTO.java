package com.api.dto;

import com.api.model.User;

import java.util.List;

public class UserRequestDTO {

    private String name;
    private String username;
    private String password;
    private List<String> role;

    public UserRequestDTO() {
    }

    public User turnForObject(){
        return new User(name, username, password, role);

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRole(){
        return role;
    }

    public void setRole(List<String> role){
        this.role = role;
    }
}
