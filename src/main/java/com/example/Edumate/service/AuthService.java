package com.example.Edumate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Edumate.dto.AuthResponseDTO;
import com.example.Edumate.dto.LoginRequestDTO;
import com.example.Edumate.dto.RegisterRequestDTO;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.UserRepo;
import com.example.Edumate.security.JwtService;
@Service
public class AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    //register
    public AuthResponseDTO register(RegisterRequestDTO request){
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("EMail already exists");
        }
        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        User savedUser=userRepo.save(user);

        return new AuthResponseDTO(
                "Registration successful",
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                null //no token while registering
        );
    }

    //login
    public AuthResponseDTO login(LoginRequestDTO request){
        User user=userRepo.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        String token=jwtService.generateToken(user.getEmail());
        return new AuthResponseDTO(
            "Login Successful",
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            token);
    }
    //get current logged in user
    public User getCurrentUser(Authentication authentication){
        String email=authentication.getName();
        return userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
    }
}
