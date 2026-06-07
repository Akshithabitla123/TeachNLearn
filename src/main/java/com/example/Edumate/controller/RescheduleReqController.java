package com.example.Edumate.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.dto.RescheduleDTO;
import com.example.Edumate.service.RescheduleReqService;

@RestController
@RequestMapping("/reschedule")
public class RescheduleReqController {
    @Autowired
    private RescheduleReqService service;
    @PostMapping("/create/{bookingId}")
    public RescheduleDTO create(@PathVariable Long bookingId){
        return service.createRequest(bookingId);
    }
    //mentor gives slots
    @PutMapping("/propose/{bookingId}")
    public RescheduleDTO propose(@PathVariable Long bookingId,Authentication authentication,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime slot1,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime slot2,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime slot3){
        return service.proposeSlots(bookingId,authentication.getName(), slot1, slot2, slot3);
    }
    //get the slots
    @GetMapping("/slots/{bookingId}")
    public RescheduleDTO getSlots(@PathVariable Long bookingId,Authentication auth){
        return service.getSlots(bookingId,auth.getName());
    }
    //student accepts proposal
    @PutMapping("/accept/{bookingId}")
    public String accept(@PathVariable Long bookingId,Authentication auth,@RequestParam LocalDateTime slot){
        return service.acceptSlot(bookingId,auth.getName(),slot);
    }
    //reject
    @DeleteMapping("/reject/{id}")
    public RescheduleDTO reject(@PathVariable Long id,Authentication auth){
        return service.rejectRequest(id,auth.getName());
    }       
}
