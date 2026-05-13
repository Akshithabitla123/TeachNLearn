package com.example.Edumate.dto;

public class UpdateProfileDTO {
    private String bio;
    private String linkedIn;
    private String github;
    private String profileImage;

    public UpdateProfileDTO(String bio, String linkedIn, String github, String profileImage) {
        this.bio = bio;
        this.linkedIn = linkedIn;
        this.github = github;
        this.profileImage = profileImage;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
