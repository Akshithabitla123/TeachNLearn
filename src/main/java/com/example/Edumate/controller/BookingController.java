package com.example.Edumate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import com.example.Edumate.model.User;
import com.example.Edumate.repository.UserRepo;
import com.example.Edumate.service.BookingService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserRepo userRepo;
    @PostMapping
    public BookingResponseDTO createBooking(@Valid @RequestBody BookingDTO dto){
        return bookingService.createBooking(dto);
    }
    //get bookings of mentor
    @GetMapping("/mentor-bookings")
    public List<BookingResponseDTO> getMentorBookings(Authentication authentication){
        String email=authentication.getName();
        User mentor=userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not found"));
        return bookingService.getMentorBookings(mentor.getId());
    }
    //get bookings of student
    @GetMapping("/my-bookings")
    public List<BookingResponseDTO> getStudentBookings(Authentication authentication){
        String email=authentication.getName();
        return bookingService.getStudentBookingsByEmail(email);
    }
    //get details of that booking
    @GetMapping("/{bookingId}")
    public BookingResponseDTO getBookingById(@PathVariable Long bookingId){
        return bookingService.getBookingById(bookingId);
    }
    //update status (accepted,rejected) by mentor
    @PutMapping("/{bookingId}/status")
    public BookingResponseDTO updateStatus(@PathVariable Long bookingId,Authentication authentication, @RequestParam Status status){
        String email=authentication.getName();
        User user=userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not found"));
        return bookingService.updateStatus(bookingId,user.getId(),status);
    }
    //mark session completed
    @PutMapping("/{bookingId}/complete")
    public BookingResponseDTO markCompleted(@PathVariable Long bookingId, Authentication authentication){
        String email=authentication.getName();
        User user=userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not found"));
        return bookingService.markCompleted(bookingId,user.getId());
    }
   //get status of current booking
   @GetMapping("/check")
   public BookingResponseDTO checkBooking(Authentication authentication,@RequestParam Long skillId){
    String email=authentication.getName();
    User user=userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not found"));
    return bookingService.getExistingBooking(user.getId(),skillId);
   }
    //cancel booking
    @PutMapping("/{bookingId}/cancel")
    public BookingResponseDTO cancelBooking(@PathVariable Long bookingId,Authentication auth){
        return bookingService.cancelBooking(bookingId,auth.getName());
    }
}
