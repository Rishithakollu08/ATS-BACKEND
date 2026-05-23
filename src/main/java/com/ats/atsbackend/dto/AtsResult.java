package com.ats.atsbackend.dto;

import java.util.List;
import java.util.Map;

public class AtsResult {
    private int score;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> resumeSkills;
    private Map<String, List<Map<String, String>>> learningPlan; // ✅ updated type
    private Map<String, List<Map<String, String>>> recommendations; // ✅ updated type

    public AtsResult(int score, List<String> matchedSkills,
                     List<String> missingSkills, List<String> resumeSkills,
                     Map<String, List<Map<String, String>>> learningPlan,
                     Map<String, List<Map<String, String>>> recommendations) {
        this.score = score;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.resumeSkills = resumeSkills;
        this.learningPlan = learningPlan;
        this.recommendations = recommendations;
    }

    public int getScore() { return score; }
    public List<String> getMatchedSkills() { return matchedSkills; }
    public List<String> getMissingSkills() { return missingSkills; }
    public List<String> getResumeSkills() { return resumeSkills; }
    public Map<String, List<Map<String, String>>> getLearningPlan() { return learningPlan; }
    public Map<String, List<Map<String, String>>> getRecommendations() { return recommendations; }
}