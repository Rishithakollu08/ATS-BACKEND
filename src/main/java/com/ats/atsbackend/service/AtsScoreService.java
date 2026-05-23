package com.ats.atsbackend.service;

import com.ats.atsbackend.dto.AtsResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AtsScoreService {

    private final LearningRecommendationService learningRecommendationService;

    public AtsScoreService(LearningRecommendationService learningRecommendationService) {
        this.learningRecommendationService = learningRecommendationService;
    }

    public AtsResult calculateScore(List<String> resumeSkills, List<String> jobSkills) {

        if (jobSkills == null || jobSkills.isEmpty()) {
            return new AtsResult(0, List.of(), List.of(), resumeSkills, Map.of(), Map.of());
        }

        List<String> resumeLower = resumeSkills.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        List<String> matched = jobSkills.stream()
                .filter(s -> resumeLower.contains(s.toLowerCase()))
                .collect(Collectors.toList());

        List<String> missing = jobSkills.stream()
                .filter(s -> !resumeLower.contains(s.toLowerCase()))
                .collect(Collectors.toList());

        int score = (int) ((double) matched.size() / jobSkills.size() * 100);

        Map<String, List<Map<String, String>>> learningPlan =
                learningRecommendationService.getResources(missing);

        Map<String, List<Map<String, String>>> recommendations =
                learningRecommendationService.getResources(missing);

        // ✅ return actual calculated values, not hardcoded empty ones
        return new AtsResult(score, matched, missing, resumeSkills, learningPlan, recommendations);
    }
}