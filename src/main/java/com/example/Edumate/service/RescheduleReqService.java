package com.example.Edumate.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.BookingResponseDTO;
import com.example.Edumate.dto.RescheduleDTO;
import com.example.Edumate.model.Booking;
import com.example.Edumate.model.RescheduleRequest;
import com.example.Edumate.repository.BookingRepo;
import com.example.Edumate.repository.RescheduleReqRepo;

@Service
public class RescheduleReqService {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private RescheduleReqRepo rescheduleReqRepo;
    //mapper
    private BookingResponseDTO mapBooking(Booking booking){

    BookingResponseDTO dto = new BookingResponseDTO();

    dto.setId(booking.getId());
    dto.setSessionDate(booking.getSessionDate());
    dto.setStatus(booking.getStatus());
    dto.setMessage(booking.getMessage());

    dto.setMentorCompleted(booking.isMentorCompleted());
    dto.setStudentCompleted(booking.isStudentCompleted());
    dto.setReviewed(booking.isReviewed());

    return dto;
}
    public RescheduleDTO mapToDto(RescheduleRequest request){
        RescheduleDTO dto=new RescheduleDTO();
        dto.setBooking(mapBooking(request.getBooking()));
        dto.setSlot1(request.getSlot1());
        dto.setSlot2(request.getSlot2());
        dto.setSlot3(request.getSlot3());
        dto.setAcceptedSlot(request.getAcceptedSlot());
        return dto;
    }
    public RescheduleDTO createRequest(Long bookingId){
        Optional<RescheduleRequest> existing=rescheduleReqRepo.findByBookingId(bookingId);
        if(existing.isPresent()){
            return mapToDto(existing.get());
        }
        Booking booking=bookingRepo.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found"));
        RescheduleRequest request=new RescheduleRequest();
        request.setBooking(booking);
        return mapToDto(rescheduleReqRepo.save(request));
    }
    
    public RescheduleDTO proposeSlots(Long bookingId,LocalDateTime slot1,LocalDateTime slot2,LocalDateTime slot3){
        Booking booking=bookingRepo.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found"));
        booking.setStatus(Status.RESCHEDULE_REQUESTED);
        RescheduleRequest request=rescheduleReqRepo.findByBookingId(bookingId).orElseThrow(()->new RuntimeException("Request not found"));
        request.setSlot1(slot1);
        request.setSlot2(slot2);
        request.setSlot3(slot3);
        request.setBooking(booking);
        return mapToDto(rescheduleReqRepo.save(request));
    }
    //student accept a slot
    public String acceptSlot(Long bookingId,LocalDateTime chosenSlot){
        RescheduleRequest req=rescheduleReqRepo.findByBookingId(bookingId).orElseThrow(()->new RuntimeException("Request not found"));
         if (!chosenSlot.equals(req.getSlot1()) &&
        !chosenSlot.equals(req.getSlot2()) &&
        !chosenSlot.equals(req.getSlot3())) {
            throw new RuntimeException("Invalid slot");
        }
        req.setAcceptedSlot(chosenSlot);
        Booking booking=req.getBooking();
        booking.setStatus(Status.RESCHEDULED);
        booking.setSessionDate(chosenSlot);
        bookingRepo.save(booking);
        rescheduleReqRepo.save(req);
        return "Session rescheduled";
    }

    //reject request
    public RescheduleDTO rejectRequest(Long requestId) {
        RescheduleRequest req = rescheduleReqRepo.findById(requestId)
                .orElseThrow();
        Booking booking=req.getBooking();
        booking.setStatus(Status.REJECTED);
        bookingRepo.save(booking);
        RescheduleDTO dto=mapToDto(req);
        rescheduleReqRepo.delete(req);
        return dto;
    }

    public RescheduleDTO getSlots(Long bookingId) {
        RescheduleRequest req=rescheduleReqRepo.findByBookingId(bookingId).orElseThrow(()->new RuntimeException("No request found"));
        return mapToDto(req);
    }
}
