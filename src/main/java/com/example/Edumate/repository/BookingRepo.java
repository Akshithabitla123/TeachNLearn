package com.example.Edumate.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.model.Booking;

public interface BookingRepo extends JpaRepository<Booking,Long> {
    List<Booking> findByMentorId(Long mentorId);
    List<Booking> findByStudentId(Long studentId);
    Optional<Booking> findFirstByStudentIdAndSkillIdOrderBySessionDate(Long studentId, Long skillId);
    Optional<Booking> findFirstByStudentIdAndSkillIdAndStatusInOrderBySessionDate(Long studentId,Long skillId, List<Status> statuses);
    @Query("""
            SELECT COUNT(DISTINCT b.student.id)
            FROM Booking b
            WHERE b.mentor.id=:mentorId
            AND b.status=:status
        """)
        int countCompletedLearners(Long mentorId, Status status);
}
