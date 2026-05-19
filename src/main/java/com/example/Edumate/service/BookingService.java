package com.example.Edumate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.BookingDTO;
import com.example.Edumate.model.Booking;
import com.example.Edumate.model.Skill;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.BookingRepo;
import com.example.Edumate.repository.SkillRepo;
import com.example.Edumate.repository.UserRepo;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private RescheduleReqService service;
    //create booking
    public Booking createBooking(BookingDTO dto){
        User student=userRepo.findById(dto.getStudentId()).
                orElseThrow(()->new RuntimeException("Student Not found"));
        User mentor=userRepo.findById(dto.getMentorId())
                .orElseThrow(()->new RuntimeException("Mentor not found"));
        Skill skill=skillRepo.findById(dto.getSkillId())
                .orElseThrow(()->new RuntimeException("Skill not found"));
        Optional<Booking> existingBooking=bookingRepo.findByStudentIdAndSkillId(student.getId(), skill.getId());
        if(existingBooking.isPresent()){
            throw new RuntimeException("Session already booked");
        }
        Booking booking=new Booking();
        booking.setStudent(student);
        booking.setMentor(mentor);
        booking.setSkill(skill);
        booking.setSessionDate(dto.getSessionDate());
        booking.setMessage(dto.getMessage());
        booking.setStatus(Status.PENDING);
        return bookingRepo.save(booking);
    }
    //get bookings for mentor
    public List<Booking> getMentorBookings(Long mentorId){
        return bookingRepo.findByMentorId(mentorId);
    }
    //get bookings by a student
    public List<Booking> getStudentBookings(Long studentId){
        return bookingRepo.findByStudentId(studentId);
    }
    //update the status of the session
    //accepted and rejected-> mentor
    //completed should be by both mentor and student
    public Booking updateStatus(Long bookingId,Long userId,Status status){
        Booking booking=bookingRepo.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
        Long mentorId=booking.getMentor().getId();
        //accept or reject only by mentor
        if(status==Status.ACCEPTED){
            if(!mentorId.equals(userId)){
                throw new RuntimeException("Only mentor can accept/reject");
            }
            booking.setStatus(status);
        }
        else if(status==Status.REJECTED){
            if(!mentorId.equals(userId)){
                throw new RuntimeException("Only mentor can accept/reject");
            }
            
            service.createRequest(bookingId);
        }
        return bookingRepo.save(booking);
    }
    public Booking markCompleted(Long bookingId,Long userId){
        Booking booking=bookingRepo.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
        Long mentorId=booking.getMentor().getId();
        Long studentId=booking.getStudent().getId();
        if(mentorId.equals(userId)){
            booking.setMentorCompleted(true);
        }
        else if(studentId.equals(userId)){
            booking.setStudentCompleted(true);
        }
        else{
            throw new RuntimeException("Unauthorized user");
        }
        if(booking.isMentorCompleted() && booking.isStudentCompleted()){
            booking.setStatus(Status.COMPLETED);
        }
        return bookingRepo.save(booking);
    }
    //get booking details
    public Booking getBookingById(Long bookingId){
        return bookingRepo.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
    }

    //get existing booking of user
    public Booking getExistingBooking(Long studentId,Long skillId){
        return bookingRepo.findByStudentIdAndSkillId(studentId, skillId).orElse(null);
    }

}
