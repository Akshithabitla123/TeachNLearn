package com.example.Edumate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private int stars;
    @Column(length=100)
    private String comment;
    @ManyToOne
    @JoinColumn(name="student_id")
    private User student;
    @ManyToOne
    @JoinColumn(name="mentor_id")
    private User mentor;
    @OneToOne
    @JoinColumn(name="booking_id")
    private Booking booking;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }



    
}
