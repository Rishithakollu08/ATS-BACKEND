package com.ats.atsbackend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillExtractorService {

    // predefined skill database (you can expand later)
    private static final String[] SKILLS = {
            "java", "spring", "spring boot", "sql", "mysql",
            "react", "html", "css", "javascript", "python",
            "hibernate", "docker"
    };

    public List<String> extractSkills(String resumeText) {

        List<String> foundSkills = new ArrayList<>();

        String lowerText = resumeText.toLowerCase();

        for (String skill : SKILLS) {
            if (lowerText.contains(skill)) {
                foundSkills.add(skill);
            }
        }

        return foundSkills;
    }
}