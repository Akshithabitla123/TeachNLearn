package com.example.Edumate.dto;

import jakarta.validation.constraints.Size;

public class RatingDTO {
    private Long bookingId;
    private int stars;
    @Size(min=3,max=200,message="Comment should be less than 200 characters")
    private String comment;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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
