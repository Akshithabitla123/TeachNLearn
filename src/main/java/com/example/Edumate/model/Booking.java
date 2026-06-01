package com.example.Edumate.model;

import java.time.LocalDateTime;

import com.example.Edumate.Enum.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //user who books the session
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="student_id")
    private User student;
    //mentor
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mentor_id")
    private User mentor;
    //skill
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="skill_id")
    private Skill skill;
    private LocalDateTime sessionDate;
    private String message;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean mentorCompleted=false;
    private boolean studentCompleted=false;
    private boolean reviewed=false;

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isMentorCompleted() {
        return mentorCompleted;
    }

    public void setMentorCompleted(boolean mentorCompleted) {
        this.mentorCompleted = mentorCompleted;
    }

    public boolean isStudentCompleted() {
        return studentCompleted;
    }

    public void setStudentCompleted(boolean studentCompleted) {
        this.studentCompleted = studentCompleted;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

}
