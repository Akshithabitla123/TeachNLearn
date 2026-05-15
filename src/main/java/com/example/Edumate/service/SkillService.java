package com.example.Edumate.service;

import com.example.Edumate.dto.SkillResponseDTO;
import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.model.Skill;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.SkillRepo;
import com.example.Edumate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private UserRepo userRepo;

    //create skill
    public Skill createSkill(Long userId, Skill skill){
        //find user
        User user=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        //attach user to skill
        skill.setUser(user);
        return skillRepo.save(skill);
    }
    public SkillResponseDTO mapToDTO(Skill skill){
        UserDTO userDTO=new UserDTO(
                skill.getUser().getId(),
                skill.getUser().getName(),
                skill.getUser().getEmail(),
                skill.getUser().getBio(),
                skill.getUser().getRating(),
                skill.getUser().getProfileImage(),
                skill.getUser().getLinkedIn(),
                skill.getUser().getGithub(),
                skill.getUser().isVerified());
        return new SkillResponseDTO(
                skill.getId(),
                skill.getTitle(),
                skill.getDescription(),
                skill.getCategory(),
                skill.getExperienceLevel(),
                skill.getPrice(),
                skill.isVerified(),
                skill.getThumbnail(),
                userDTO
        );
    }
    //return all skills
    public List<SkillResponseDTO> getAllSkills(){
        return skillRepo.findAll().stream().map(this::mapToDTO).toList();
    }
    //get skill by id
    public Skill getSkillById(Long id){
        return skillRepo.findById(id).orElseThrow(()->new RuntimeException("Skill not found"));
    }
    //search skills
    public List<SkillResponseDTO> searchSkills(String keyword) {
        return skillRepo
                .findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }



    //get skills of user
    public List<SkillResponseDTO> getSkillsByUser(Long userId){
        return skillRepo.findByUserId(userId) .stream()
                .map(this::mapToDTO)
                .toList();
    }
    //filter by category
    public List<SkillResponseDTO> getSkillsByCategory(String category){
        return skillRepo.findByCategoryIgnoreCase(category) .stream()
                .map(this::mapToDTO)
                .toList();
    }
    //filter by experience level
    public List<SkillResponseDTO> getSkillsByExperienceLevel(String level){
        return skillRepo.findByExperienceLevelIgnoreCase(level) .stream()
                .map(this::mapToDTO)
                .toList();
    }
    //get searched skill of user
    public SkillResponseDTO getSkillOfUser(
            Long userId,
            Long skillId
    ) {

        Skill skill = skillRepo
                .findByIdAndUserId(skillId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found"));

        return mapToDTO(skill);
    }
}
