package com.example.Edumate.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Edumate.Enum.Status;
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
    public RescheduleRequest createRequest(Long bookingId){
        Optional<RescheduleRequest> existing=rescheduleReqRepo.findByBookingId(bookingId);
        if(existing.isPresent()){
            return existing.get();
        }
        Booking booking=bookingRepo.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found"));
        RescheduleRequest request=new RescheduleRequest();
        booking.setStatus(Status.RESCHEDULE_REQUESTED);
        request.setBooking(booking);
        return rescheduleReqRepo.save(request);
    }
    public RescheduleRequest proposeSlots(Long bookingId,LocalDateTime slot1,LocalDateTime slot2,LocalDateTime slot3){
        RescheduleRequest request=rescheduleReqRepo.findByBookingId(bookingId).orElseThrow(()->new RuntimeException("Request not found"));
        request.setSlot1(slot1);
        request.setSlot2(slot2);
        request.setSlot3(slot3);
        return rescheduleReqRepo.save(request);
    }
    //student accept a slot
    public Booking acceptSlot(Long bookingId,LocalDateTime chosenSlot){
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
        return booking;
    }

    //reject request
    public RescheduleRequest rejectRequest(Long requestId) {
        RescheduleRequest req = rescheduleReqRepo.findById(requestId)
                .orElseThrow();
        Booking booking=req.getBooking();
        booking.setStatus(Status.REJECTED);
        bookingRepo.save(booking);
        rescheduleReqRepo.delete(req);
        return req;
    }

    public RescheduleRequest getSlots(Long bookingId) {
        return rescheduleReqRepo.findByBookingId(bookingId).orElseThrow(()->new RuntimeException("No request found"));
    }
}
