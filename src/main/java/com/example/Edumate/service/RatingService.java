package com.example.Edumate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.RatingDTO;
import com.example.Edumate.dto.ReviewResponseDTO;
import com.example.Edumate.model.Booking;
import com.example.Edumate.model.Rating;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.BookingRepo;
import com.example.Edumate.repository.RatingRepo;
import com.example.Edumate.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepo ratingRepo;
    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    public String addRating(Long studentId,RatingDTO dto){
        Booking booking=bookingRepo.findById(dto.getBookingId()).
                orElseThrow(()->new RuntimeException("Booking Not found"));
        if(!booking.getStudent().getId().equals(studentId)){
            throw new RuntimeException("Only the student with this booking can rate it");
        }
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
        return "Rating added Successfully";
    }
    public void updateMentorRating(User mentor){
        List<Rating> ratings=ratingRepo.findByMentorId(mentor.getId());
        double avg=ratings.stream().mapToInt(Rating::getStars).average().orElse(0);
        mentor.setRating(avg);
        userRepo.save(mentor);
    }
    //find ratings by mentorId
    public List<ReviewResponseDTO> getMentorRatings(Long mentorId) {
        List<Rating> list=ratingRepo.findByMentorId(mentorId);
        return list.stream()
        .map(rating->new ReviewResponseDTO(rating.getStudent().getName(),rating.getStars(),rating.getComment()))
        .toList();
            
    }

}
