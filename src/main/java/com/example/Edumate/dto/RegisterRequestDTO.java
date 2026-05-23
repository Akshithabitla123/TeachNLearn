package com.example.Edumate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {
    @NotBlank(message="Name required")
    @Size(min=3,max=30,message="Name should be between 0-30 characters")
    private String name;
    @NotBlank(message="Email required")
    @Email(message="Invalid Email format")
    private String email;
    @NotBlank(message="Password required")
    @Size(min=6,message="Password must contain atleast 6 charcaters")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
