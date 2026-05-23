package com.ats.atsbackend.controller;

import com.ats.atsbackend.service.AtsScoreService;
import org.springframework.web.bind.annotation.*;
import com.ats.atsbackend.dto.AtsRequest;
import com.ats.atsbackend.dto.AtsResult;

@RestController
@RequestMapping("/api/ats")
public class AtsController {

    private final AtsScoreService atsScoreService;

    public AtsController(AtsScoreService atsScoreService) {
        this.atsScoreService = atsScoreService;
    }

    @PostMapping("/score")
    public AtsResult getScore(@RequestBody AtsRequest request) {

        return atsScoreService.calculateScore(
                request.getResumeSkills(),
                request.getJobSkills()
        );
    }
}