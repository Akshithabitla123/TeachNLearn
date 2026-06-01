package com.example.Edumate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edumate.dto.SkillResponseDTO;
import com.example.Edumate.model.Skill;
import com.example.Edumate.service.SkillService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;
    @PostMapping("/{userId}")
    public String createSkill(@PathVariable Long userId,@Valid @RequestBody Skill skill){
         return skillService.createSkill(userId, skill);
    }
    //get all skills
    @GetMapping
    public List<SkillResponseDTO> getAllSkills(){
        return skillService.getAllSkills();
    }
    //get skill by id
    @GetMapping("/{id}")
    public SkillResponseDTO getSkillById(@PathVariable Long id){
        return skillService.getSkillById(id);
    }
    //search skill
    @GetMapping("/search")
    public List<SkillResponseDTO> searchSkills(@RequestParam String keyword){
        return skillService.searchSkills(keyword);
    }
    //get skills of user
    @GetMapping("/user/{userId}")
    public List<SkillResponseDTO> getSkillsByUser(@PathVariable Long userId){
        return skillService.getSkillsByUser(userId);
    }
    //filter by category
    @GetMapping("/category")
    public List<SkillResponseDTO> getSkillsByCategory(@RequestParam String category){
        return skillService.getSkillsByCategory(category);
    }
    //filter by experience level
    @GetMapping("/level")
    public List<SkillResponseDTO> getSkillsByExperienceLevel(@RequestParam String level){
        return skillService.getSkillsByExperienceLevel(level);
    }
    //get specific of a specific user
    @GetMapping("/user/{userId}/skill/{skillId}")
    public SkillResponseDTO getSkillOfUser(@PathVariable Long userId,@PathVariable Long skillId){
        return skillService.getSkillOfUser(userId, skillId);
    }
    //get random skills to display in trending skills section
    @GetMapping("/random")
    public List<SkillResponseDTO> getRandomSkills(){
        List<SkillResponseDTO> skills=skillService.getRandomSkills();
        return skills;
    }
}
