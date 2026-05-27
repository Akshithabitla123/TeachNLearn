package com.example.Edumate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.dto.AdminLoginDTO;
import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.service.AdminService;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    //login admin
    @PostMapping("/login")
    public String loginAdmin(@RequestBody AdminLoginDTO dto){
        return adminService.login(dto);
    }
    //get all users
    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return adminService.getAllUsers();
    }
    //get specific user
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return adminService.getUserById(id);
    }
    //verify user
    @PutMapping("/verify/{id}")
    public String verifyUser(@PathVariable Long id){
        return adminService.verifyUser(id);
    }
}
