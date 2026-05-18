package com.example.Edumate.repository;

import com.example.Edumate.dto.BookingDTO;
import com.example.Edumate.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long> {
    List<Booking> findByMentorId(Long mentorId);
    List<Booking> findByStudentId(Long studentId);
    Optional<Booking> findByStudentIdAndSkillId(Long studentId,Long skillId);
}
