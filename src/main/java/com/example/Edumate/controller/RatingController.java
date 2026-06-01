package com.example.Edumate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.dto.RatingDTO;
import com.example.Edumate.dto.ReviewResponseDTO;
import com.example.Edumate.service.RatingService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    //add rating
    @PostMapping("/{studentId}")
    public String addRating(@PathVariable Long studentId,@Valid @RequestBody RatingDTO dto){
        return ratingService.addRating(studentId,dto);
    }
    //get mentor ratings
    @GetMapping("/mentor/{mentorId}")
    public List<ReviewResponseDTO> getMentorRating(@PathVariable Long mentorId){
        return ratingService.getMentorRatings(mentorId);
    }
}
