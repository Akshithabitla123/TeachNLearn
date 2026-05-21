package com.example.Edumate.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.model.Booking;
import com.example.Edumate.model.RescheduleRequest;
import com.example.Edumate.service.RescheduleReqService;


@RestController
@RequestMapping("/reschedule")
public class RescheduleReqController {
    @Autowired
    private RescheduleReqService service;
    @PostMapping("/create/{bookingId}")
    public RescheduleRequest create(@PathVariable Long bookingId){
        return service.createRequest(bookingId);
    }
    //mentor gives slots
    @PutMapping("/propose/{id}")
    public RescheduleRequest propose(@PathVariable Long id,@RequestParam LocalDateTime slot1,@RequestParam LocalDateTime slot2,@RequestParam LocalDateTime slot3){
        return service.proposeSlots(id, slot1, slot2, slot3);
    }
    //get the slots
    @GetMapping("/slots/{bookingId}")
    public RescheduleRequest getSlots(@PathVariable Long bookingId){
        return service.getSlots(bookingId);
    }
    //student accepts proposal
    @PutMapping("/accept/{id}")
    public Booking accept(@PathVariable Long id,@RequestParam LocalDateTime slot){
        return service.acceptSlot(id, slot);
    }
    //reject
    @DeleteMapping("/reject/{id}")
    public RescheduleRequest reject(@PathVariable Long id){
        return service.rejectRequest(id);
    }       
}
