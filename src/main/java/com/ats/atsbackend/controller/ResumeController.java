package com.ats.atsbackend.controller;

import com.ats.atsbackend.dto.AtsResult;
import com.ats.atsbackend.service.AtsScoreService;
import com.ats.atsbackend.service.LearningRecommendationService;
import com.ats.atsbackend.service.ResumeParserService;
import com.ats.atsbackend.service.SkillExtractorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "http://localhost:5173")
public class ResumeController {

    private final ResumeParserService resumeParserService;
    private final SkillExtractorService skillExtractorService;
    private final AtsScoreService atsScoreService;
    private final LearningRecommendationService learningRecommendationService;

    public ResumeController(
            ResumeParserService resumeParserService,
            SkillExtractorService skillExtractorService,
            AtsScoreService atsScoreService,
            LearningRecommendationService learningRecommendationService
    ) {
        this.resumeParserService = resumeParserService;
        this.skillExtractorService = skillExtractorService;
        this.atsScoreService = atsScoreService;
        this.learningRecommendationService = learningRecommendationService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) List<String> jobSkills
    ) throws Exception {

        // if no skills entered
        if (jobSkills == null) {
            jobSkills = List.of();
        }

        // extract resume text
        String text = resumeParserService.extractText(file);

        // extract skills from resume
        List<String> resumeSkills =
                skillExtractorService.extractSkills(text);

        // calculate ATS score
        AtsResult result =
                atsScoreService.calculateScore(resumeSkills, jobSkills);

        // get learning recommendations for missing skills
        Map<String, List<Map<String,String>>> recommendations =
                learningRecommendationService.getResources(
                        result.getMissingSkills()
                );

        // final response
        Map<String, Object> response = new HashMap<>();

        response.put("score", result.getScore());
        response.put("matchedSkills", result.getMatchedSkills());
        response.put("missingSkills", result.getMissingSkills());
        response.put("resumeSkills", result.getResumeSkills());

        // learning resources
        response.put("recommendations", recommendations);

        return ResponseEntity.ok(response);
    }
}