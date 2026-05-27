package com.example.Edumate.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class RescheduleRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Booking booking;
    private LocalDateTime slot1;
    private LocalDateTime slot2;
    private LocalDateTime slot3;
    private LocalDateTime acceptedSlot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public LocalDateTime getSlot1() {
        return slot1;
    }

    public void setSlot1(LocalDateTime slot1) {
        this.slot1 = slot1;
    }

    public LocalDateTime getSlot2() {
        return slot2;
    }

    public void setSlot2(LocalDateTime slot2) {
        this.slot2 = slot2;
    }

    public LocalDateTime getSlot3() {
        return slot3;
    }

    public void setSlot3(LocalDateTime slot3) {
        this.slot3 = slot3;
    }

    public LocalDateTime getAcceptedSlot() {
        return acceptedSlot;
    }

    public void setAcceptedSlot(LocalDateTime acceptedSlot) {
        this.acceptedSlot = acceptedSlot;
    }
   

}
