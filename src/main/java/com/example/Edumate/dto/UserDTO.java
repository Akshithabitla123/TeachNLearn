package com.example.Edumate.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private Double rating;
    private String profileImage;
    private String linkedIn;
    private String github;
    private boolean verified;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String bio, Double rating, String profileImage, String linkedIn, String github, boolean verified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.rating = rating;
        this.profileImage = profileImage;
        this.linkedIn = linkedIn;
        this.github = github;
        this.verified = verified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
