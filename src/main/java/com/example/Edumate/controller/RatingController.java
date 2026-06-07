package com.example.Edumate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.dto.RatingDTO;
import com.example.Edumate.dto.ReviewResponseDTO;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.UserRepo;
import com.example.Edumate.service.RatingService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserRepo userRepo;
    //add rating
    @PostMapping()
    public String addRating(Authentication authentication,@Valid @RequestBody RatingDTO dto){
        String email=authentication.getName();
        User user=userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not found"));
        return ratingService.addRating(user.getId(),dto);
    }
    //get mentor ratings
    @GetMapping("/mentor/{mentorId}")
    public List<ReviewResponseDTO> getMentorRating(@PathVariable Long mentorId){
        return ratingService.getMentorRatings(mentorId);
    }
}
