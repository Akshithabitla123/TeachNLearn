package com.example.Edumate.service;

import com.example.Edumate.dto.AuthResponseDTO;
import com.example.Edumate.dto.LoginRequestDTO;
import com.example.Edumate.dto.RegisterRequestDTO;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepo userRepo;

    //register
    public AuthResponseDTO register(RegisterRequestDTO request){
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("EMail already exists");
        }
        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        User savedUser=userRepo.save(user);

        return new AuthResponseDTO(
                "Registration successful",
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    //login
    public AuthResponseDTO login(LoginRequestDTO request){
        User user=userRepo.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
        if(!user.getPassword().equals(request.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        return new AuthResponseDTO("Login Successful", user.getId(), user.getName(),user.getEmail());
    }
}
