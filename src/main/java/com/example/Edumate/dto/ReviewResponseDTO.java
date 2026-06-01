package com.example.Edumate.dto;

public class ReviewResponseDTO {
    private String learnerName;
    private int stars;
    private String comment;
    
    public ReviewResponseDTO(String learnerName, int stars, String comment) {
        this.learnerName = learnerName;
        this.stars = stars;
        this.comment = comment;
    }
    public String getLearnerName() {
        return learnerName;
    }
    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
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
    
}
