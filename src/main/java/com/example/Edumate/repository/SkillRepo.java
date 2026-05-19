package com.example.Edumate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Edumate.model.Skill;

@Repository
public interface SkillRepo extends JpaRepository<Skill,Long> {
    //search skills by title
    List<Skill> findByTitleContainingIgnoreCase(String keyword);
    //find skills by category
    List<Skill> findByCategoryIgnoreCase(String experienceLevel);
    //find skills by  experienceLevel
    List<Skill> findByExperienceLevelIgnoreCase(String experienceLevel);
    //find skills of a specific user
    List<Skill> findByUserId(Long userId);
    //count skills per user
    int countByUserId(Long userId);
    //find specific skill of a specific user
    Optional<Skill> findByIdAndUserId(
            Long skillId,
            Long userId
    );

}
