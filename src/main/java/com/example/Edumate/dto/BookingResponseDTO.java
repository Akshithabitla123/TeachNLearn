package com.example.Edumate.dto;

import java.time.LocalDateTime;

import com.example.Edumate.Enum.Status;


public class BookingResponseDTO {
    private Long id;
    private UserDTO mentor;
    private UserDTO student;
    private SkillResponseDTO skill;
    private LocalDateTime sessionDate;
    private Status status;
    private String message;
    private boolean mentorCompleted;
    private boolean studentCompleted;
    private boolean reviewed;
    // private ReviewResponseDTO review;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getMentor() {
        return mentor;
    }

    public void setMentor(UserDTO mentor) {
        this.mentor = mentor;
    }

    public UserDTO getStudent() {
        return student;
    }

    public void setStudent(UserDTO student) {
        this.student = student;
    }

    public SkillResponseDTO getSkill() {
        return skill;
    }

    public void setSkill(SkillResponseDTO skill) {
        this.skill = skill;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    // public ReviewResponseDTO getReview() {
    //     return review;
    // }

    // public void setReview(ReviewResponseDTO review) {
    //     this.review = review;
    // }

    
}
