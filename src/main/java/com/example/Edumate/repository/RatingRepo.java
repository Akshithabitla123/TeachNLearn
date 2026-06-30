package com.example.Edumate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Edumate.model.Booking;
import com.example.Edumate.model.Rating;

public interface RatingRepo extends JpaRepository<Rating,Long>{
    boolean existsByBooking(Booking booking);
    List<Rating> findByMentorId(Long mentorId);
}
