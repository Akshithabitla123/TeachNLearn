package com.example.Edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Edumate.model.RescheduleRequest;

@Repository
public interface RescheduleReqRepo extends JpaRepository<RescheduleRequest,Long>{
    
}
