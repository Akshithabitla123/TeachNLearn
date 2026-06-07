package com.example.Edumate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.SummaryDTO;
import com.example.Edumate.dto.UpdateProfileDTO;
import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.BookingRepo;
import com.example.Edumate.repository.SkillRepo;
import com.example.Edumate.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private BookingRepo bookingRepo;

    //save user
    public String saveUser(User user){
        if(userRepo.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        if(userRepo.existsByName(user.getName())){
            throw new RuntimeException("User name already exists");
        }

        userRepo.save(user);
        return "Success";
    }
    private UserDTO mapToDTO(User user){
        UserDTO dto = new UserDTO();
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
    //get user by id
    public UserDTO getUserById(Long id){
        User user =userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        return mapToDTO(user);
    }
    //get user by email
    public UserDTO getUserByEmail(String email){
        User user =userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    //delete user
    public void deleteUser(Long id){
        User user=userRepo.findById(id).orElseThrow(()->new RuntimeException("User Already deleted"));
        userRepo.delete(user);
    }

    //update profile
    public User updateProfile(String email, UpdateProfileDTO request){
        User user=userRepo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        user.setBio(request.getBio());
        user.setLinkedIn(request.getLinkedIn());
        user.setGithub(request.getGithub());
        user.setProfileImage(request.getProfileImage());
        return userRepo.save(user);
    }
    //get summary
    public SummaryDTO getSummary(String email) {
        User user=userRepo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        Long userId=user.getId();
        SummaryDTO dto=new SummaryDTO();
        int totalSkills=skillRepo.countByUserId(userId);
        dto.setTotalSkills(totalSkills);
        int totalLearners=bookingRepo.countCompletedLearners(user.getId(),Status.COMPLETED);
        dto.setTotalStudents(totalLearners);//set to some random
        dto.setRating(user.getRating()==null?0: user.getRating());
        return dto;
    }
    //get top 3 mentors
    public List<User> getTopMentors(){
        return userRepo.findTop3ByOrderByRatingDesc();
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

}
