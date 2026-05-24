package com.example.Edumate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.RatingDTO;
import com.example.Edumate.model.Booking;
import com.example.Edumate.model.Rating;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.BookingRepo;
import com.example.Edumate.repository.RatingRepo;
import com.example.Edumate.repository.UserRepo;

@Service
public class RatingService {
    @Autowired
    private RatingRepo ratingRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private UserRepo userRepo;
    public Rating addRating(Long studentId,RatingDTO dto){
        Booking booking=bookingRepo.findById(dto.getBookingId()).
                orElseThrow(()->new RuntimeException("Booking Not found"));
        if(booking.getStatus()!=Status.COMPLETED){
            throw new RuntimeException("Session not completed");
        }
        if(ratingRepo.existsByBooking(booking)){
            throw new RuntimeException("Session already rated");
        }
        Rating rating=new Rating();
        rating.setStars(dto.getStars());
        rating.setComment(dto.getComment());
        rating.setStudent(booking.getStudent());
        rating.setMentor(booking.getMentor());
        rating.setBooking(booking);
        ratingRepo.save(rating);
        updateMentorRating(booking.getMentor());
        booking.setReviewed(true);
        bookingRepo.save(booking);
        return rating;
    }
    public void updateMentorRating(User mentor){
        List<Rating> ratings=ratingRepo.findByMentor(mentor);
        double avg=ratings.stream().mapToInt(Rating::getStars).average().orElse(0);
        mentor.setRating(avg);
        userRepo.save(mentor);
    }
    //find ratings by mentorId
    public List<Rating> getMentorRatings(Long mentorId) {
        User mentor=userRepo.findById(mentorId).
                orElseThrow(()->new RuntimeException("Mentor Not found"));
        return ratingRepo.findByMentor(mentor);
    }

}
