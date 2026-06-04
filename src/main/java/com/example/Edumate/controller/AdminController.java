package com.example.Edumate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    //get all users
    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    //get specific user
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    //verify user
    @PutMapping("/verify/{id}")
    public String verifyUser(@PathVariable Long id){
        return userService.verifyUser(id);
    }
}
