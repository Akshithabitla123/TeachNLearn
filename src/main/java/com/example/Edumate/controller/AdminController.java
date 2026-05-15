package com.example.Edumate.controller;

import com.example.Edumate.dto.AdminLoginDTO;
import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //verify user
    @PutMapping("/verify/{id}")
    public String verifyUser(@PathVariable Long id){
        return adminService.verifyUser(id);
    }
}
