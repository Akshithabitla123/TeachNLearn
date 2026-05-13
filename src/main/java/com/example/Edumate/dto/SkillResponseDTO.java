package com.example.Edumate.dto;

import jakarta.persistence.Column;

public class SkillResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String category ;
    private String experienceLevel;
    private Double price;
    private boolean verified;
    private String thumbnail;
    private UserDTO userDTO;

    public SkillResponseDTO(Long id, String title, String description, String category, String experienceLevel, Double price, boolean verified, String thumbnail, UserDTO userDTO) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.experienceLevel = experienceLevel;
        this.price = price;
        this.verified = verified;
        this.thumbnail = thumbnail;
        this.userDTO = userDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
