package com.example.Edumate.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

@Entity
public class RescheduleRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Booking booking;
    @NotNull(message="Slot1 is required")
    @Future(message="Slot1 must be in the future")
    private LocalDateTime slot1;
    @NotNull(message="Slot2 is required")
    @Future(message="Slot2 must be in the future")
    private LocalDateTime slot2;
    @NotNull(message="Slot3 is required")
    @Future(message="Slot3 must be in the future")
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
    @AssertTrue(message="Slots must be unique and non-null")
    public boolean isSlotsValid(){
        if(slot1==null || slot2==null || slot3==null){
            return false;
        }
        return !(slot1.equals(slot2) ||
                slot2.equals(slot3) ||
                slot1.equals(slot3));
    }

}
