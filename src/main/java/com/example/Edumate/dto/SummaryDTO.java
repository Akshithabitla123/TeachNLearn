package com.example.Edumate.dto;

public class SummaryDTO {
    private int totalSkills;
    private int totalStudents;
    private int rating;

    public int getTotalSkills() {
        return totalSkills;
    }

    public void setTotalSkills(int totalSkills) {
        this.totalSkills = totalSkills;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
