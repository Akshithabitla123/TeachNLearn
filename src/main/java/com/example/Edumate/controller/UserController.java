package com.example.Edumate.controller;

import com.example.Edumate.dto.SummaryDTO;
import com.example.Edumate.dto.UpdateProfileDTO;
import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.model.User;
import com.example.Edumate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //create user
    @PostMapping
    public String createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    //get all users
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    //get user by id
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    //delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    //update profile
    @PutMapping("/profile/{id}")
    public User updateProfile(@PathVariable Long id, @RequestBody UpdateProfileDTO request){
        return userService.updateProfile(id,request);
    }
    //get summary
    @GetMapping("/summary/{id}")
    public SummaryDTO getSummary(@PathVariable Long id){
        return userService.getSummary(id);
    }

}
