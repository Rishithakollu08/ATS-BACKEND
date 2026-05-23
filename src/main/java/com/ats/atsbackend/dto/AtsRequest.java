package com.ats.atsbackend.dto;

import java.util.List;

public class AtsRequest {

    private List<String> resumeSkills;
    private List<String> jobSkills;

    public List<String> getResumeSkills() {
        return resumeSkills;
    }

    public void setResumeSkills(List<String> resumeSkills) {
        this.resumeSkills = resumeSkills;
    }

    public List<String> getJobSkills() {
        return jobSkills;
    }

    public void setJobSkills(List<String> jobSkills) {
        this.jobSkills = jobSkills;
    }
}
