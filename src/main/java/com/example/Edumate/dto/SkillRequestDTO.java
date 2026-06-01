package com.example.Edumate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class SkillRequestDTO {
    @NotBlank(message="Title required")
    @Size(min=2,max=50,message="Title must be 2-50 characters")
    private String title;
    @NotBlank(message="Description required")
    @Size(min=10,max=1000,message="Description must be 10-1000 characters")
    private String description;
    @NotBlank(message="Category required")
    private String category ;//(programming,design,..)
    @NotBlank(message="Experience Level required")
    @Pattern(regexp="Beginner|Intermediate|Advanced",message="Invalid Experience level")
    private String experienceLevel;
    @NotNull(message="Price required")
    @PositiveOrZero(message="Price cannot be negative")
    private Double price;

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
}
