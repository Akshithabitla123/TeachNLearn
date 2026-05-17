package com.example.Edumate.controller;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.BookingDTO;
import com.example.Edumate.model.Booking;
import com.example.Edumate.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping
    public Booking createBooking(@RequestBody BookingDTO dto){
        return bookingService.createBooking(dto);
    }
    //get bookings of mentor
    @GetMapping("/mentor/{mentorId}")
    public List<BookingDTO> getMentorBookings(@PathVariable Long mentorId){
        return bookingService.getMentorBookings(mentorId);
    }
    //get bookings of student
    @GetMapping("/student/{studentId}")
    public List<BookingDTO> getStudentBookings(@PathVariable Long studentId){
        return bookingService.getStudentBookings(studentId);
    }
    //get details of that booking
    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable Long bookingId){
        return bookingService.getBookingById(bookingId);
    }
    //update status (accepted,rejected) by mentor
    @PutMapping("/{bookingId}/status")
    public Booking updateStatus(@PathVariable Long bookingId, @RequestParam Long userId, @RequestParam Status status){
        return bookingService.updateStatus(bookingId,userId,status);
    }
    //mark session completed
    @PutMapping("/{bookingId}/complete")
    public Booking markCompleted(@PathVariable Long bookingId, @RequestParam Long userId){
        return bookingService.markCompleted(bookingId,userId);
    }
}
