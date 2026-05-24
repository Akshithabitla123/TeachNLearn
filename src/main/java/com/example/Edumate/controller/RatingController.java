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
import com.example.Edumate.model.Rating;
import com.example.Edumate.service.RatingService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    //add rating
    @PostMapping("/{studentId}")
    public Rating addRating(@PathVariable Long studentId,@RequestBody RatingDTO dto){
        return ratingService.addRating(studentId,dto);
    }
    //get mentor ratings
    @GetMapping("/mentor/{mentorId}")
    public List<Rating> getMentorRating(@PathVariable Long mentorId){
        return ratingService.getMentorRatings(mentorId);
    }
}
