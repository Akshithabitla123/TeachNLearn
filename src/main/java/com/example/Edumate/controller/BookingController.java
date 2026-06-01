package com.example.Edumate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.BookingDTO;
import com.example.Edumate.dto.BookingResponseDTO;
import com.example.Edumate.service.BookingService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping
    public BookingResponseDTO createBooking(@Valid @RequestBody BookingDTO dto){
        return bookingService.createBooking(dto);
    }
    //get bookings of mentor
    @GetMapping("/mentor/{mentorId}")
    public List<BookingResponseDTO> getMentorBookings(@PathVariable Long mentorId){
        return bookingService.getMentorBookings(mentorId);
    }
    //get bookings of student
    @GetMapping("/student/{studentId}")
    public List<BookingResponseDTO> getStudentBookings(@PathVariable Long studentId){
        return bookingService.getStudentBookings(studentId);
    }
    //get details of that booking
    @GetMapping("/{bookingId}")
    public BookingResponseDTO getBookingById(@PathVariable Long bookingId){
        return bookingService.getBookingById(bookingId);
    }
    //update status (accepted,rejected) by mentor
    @PutMapping("/{bookingId}/status")
    public BookingResponseDTO updateStatus(@PathVariable Long bookingId, @RequestParam Long userId, @RequestParam Status status){
        return bookingService.updateStatus(bookingId,userId,status);
    }
    //mark session completed
    @PutMapping("/{bookingId}/complete")
    public BookingResponseDTO markCompleted(@PathVariable Long bookingId, @RequestParam Long userId){
        return bookingService.markCompleted(bookingId,userId);
    }
   //get status of current booking
   @GetMapping("/check")
   public BookingResponseDTO checkBooking(@RequestParam Long studentId,@RequestParam Long skillId){
    return bookingService.getExistingBooking(studentId,skillId);
   }
    //cancel booking
    @PutMapping("/{bookingId}/cancel")
    public BookingResponseDTO cancelBooking(@PathVariable Long bookingId){
        return bookingService.cancelBooking(bookingId);
    }
}
