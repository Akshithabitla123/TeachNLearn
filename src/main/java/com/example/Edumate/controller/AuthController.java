package com.example.Edumate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.dto.AuthResponseDTO;
import com.example.Edumate.dto.LoginRequestDTO;
import com.example.Edumate.dto.RegisterRequestDTO;
import com.example.Edumate.service.AuthService;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    //register
    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody RegisterRequestDTO request){
        return authService.register(request);
    }
    //login
    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO request){
        return authService.login(request);
    }

}
