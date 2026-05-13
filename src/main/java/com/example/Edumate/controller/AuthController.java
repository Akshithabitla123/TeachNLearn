package com.example.Edumate.controller;

import com.example.Edumate.dto.AuthResponseDTO;
import com.example.Edumate.dto.LoginRequestDTO;
import com.example.Edumate.dto.RegisterRequestDTO;
import com.example.Edumate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    //register
    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request){
        return authService.register(request);
    }
    //login
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request){
        return authService.login(request);
    }

}
