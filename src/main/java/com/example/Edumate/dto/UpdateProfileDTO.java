package com.example.Edumate.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateProfileDTO {
    @Size(max=300,message="Bio cannot exceed 300 characters")
    private String bio;
     @Pattern(
        regexp = "^(https?:\\/\\/)?(www\\.)?linkedin\\.com\\/.*$",
        message = "Invalid LinkedIn URL"
    )
    private String linkedIn;
    @Pattern(
        regexp = "^(https?:\\/\\/)?(www\\.)?github\\.com\\/.*$",
        message = "Invalid Github URL"
    )
    private String github;
    private String profileImage;

    public UpdateProfileDTO(){
        
    }
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
