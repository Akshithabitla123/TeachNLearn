package com.example.Edumate.service;

import com.example.Edumate.dto.AdminLoginDTO;
import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.model.Admin;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.AdminRepo;
import com.example.Edumate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private UserRepo userRepo;
    public String login(AdminLoginDTO dto){
        Admin admin=adminRepo.findByEmail(dto.getEmail()).orElseThrow(()->new RuntimeException("Admin not found"));
        if(!admin.getPassword().equals(dto.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return "Admin login successful";
    }
    //get all users
    public List<UserDTO> getAllUsers(){
        List<User> users=userRepo.findAll();
        return users.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //verify user
    public String verifyUser(Long userId){
        User user=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        user.setVerified(true);
        userRepo.save(user);
        return "User verified";
    }
    private UserDTO mapToDTO(User user){
        UserDTO dto=new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setRating(user.getRating());
        dto.setProfileImage(user.getProfileImage());
        dto.setLinkedIn(user.getLinkedIn());
        dto.setGithub(user.getGithub());
        dto.setVerified(user.isVerified());
        return dto;
    }
}
