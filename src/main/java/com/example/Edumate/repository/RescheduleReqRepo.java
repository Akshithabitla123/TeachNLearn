package com.example.Edumate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Edumate.model.RescheduleRequest;

public interface RescheduleReqRepo extends JpaRepository<RescheduleRequest,Long>{

    Optional<RescheduleRequest> findByBookingId(Long bookingId);
    
}
