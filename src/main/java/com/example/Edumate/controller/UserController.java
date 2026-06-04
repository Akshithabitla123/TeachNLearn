package com.example.Edumate.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Edumate.dto.SummaryDTO;
import com.example.Edumate.dto.UpdateProfileDTO;
import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.model.User;
import com.example.Edumate.service.UserService;

import jakarta.validation.Valid;

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
    public List<UserDTO> getAllUsers(){
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
    //uploading profile image
    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException{
        //empty file check
        if(file.isEmpty()){
            throw new RuntimeException("Please select an image");
        }
        String originalName = file.getOriginalFilename();
        if(originalName==null){
            throw new RuntimeException("Invalid file");
        }
        //allow only image types
        if(!originalName.endsWith(".jpg") && !originalName.endsWith(".jpeg") && !originalName.endsWith(".png")){
            throw new RuntimeException("Only JPG,JPEG and PNG are allowed");
        }
        //size validation upto 5mb
        if(file.getSize()>5*1024*1024){
            throw new RuntimeException("Image too large");
        }

        String cleanName = originalName.replaceAll("\\s+", "_");
        String fileName=System.currentTimeMillis()+"_"+cleanName;
        Path path=Paths.get("uploads/"+fileName);
        Files.createDirectories(path.getParent());
        Files.write(path,file.getBytes());
        return "http://localhost:9090/images/" + fileName;
    }
    //update profile
    @PutMapping("/profile/{id}")
    public User updateProfile(@PathVariable Long id,@Valid @RequestBody UpdateProfileDTO request){
        return userService.updateProfile(id,request);
    }
    //get summary
    @GetMapping("/summary/{id}")
    public SummaryDTO getSummary(@PathVariable Long id){
        return userService.getSummary(id);
    }
    //display top 3 highest rated mentors
    @GetMapping("/top-mentors")
    public List<User> getTopMentors(){
        return userService.getTopMentors();
    }

}
