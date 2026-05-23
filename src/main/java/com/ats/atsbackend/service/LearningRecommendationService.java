package com.ats.atsbackend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LearningRecommendationService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public LearningRecommendationService(ChatClient.Builder builder,
                                         ObjectMapper objectMapper) {
        this.chatClient = builder.build();
        this.objectMapper = objectMapper;
    }

    public Map<String, List<Map<String, String>>> getResources(List<String> missingSkills) {

        if (missingSkills == null || missingSkills.isEmpty()) {
            return new HashMap<>();
        }

        String prompt = """
            You are a tech learning assistant. For each skill below, give EXACTLY 4 resources.
            Return ONLY raw JSON. No markdown, no explanation, no code fences.

            Format:
            {
              "SkillName": [
                { "label": "Short title", "url": "https://...", "type": "youtube" },
                { "label": "Short title", "url": "https://...", "type": "docs" },
                { "label": "Short title", "url": "https://...", "type": "roadmap" },
                { "label": "Short title", "url": "https://...", "type": "practice" }
              ]
            }

            Rules:
            - type must be exactly one of: youtube, docs, roadmap, practice
            - youtube → real YouTube search URL: https://www.youtube.com/results?search_query=skill+tutorial
            - docs → official documentation URL for that skill
            - roadmap → https://roadmap.sh/skillname or best learning path URL
            - practice → LeetCode, HackerRank, Exercism, or similar

            Skills: %s
            """.formatted(String.join(", ", missingSkills));

        try {
            String response = chatClient
                    .prompt(prompt)
                    .call()
                    .content();

            String cleaned = response
                    .replaceAll("(?s)```json", "")
                    .replaceAll("```", "")
                    .trim();

            return objectMapper.readValue(
                    cleaned,
                    new TypeReference<Map<String, List<Map<String, String>>>>() {}
            );

        } catch (Exception e) {
            // Fallback with real URLs
            Map<String, List<Map<String, String>>> fallback = new HashMap<>();
            for (String skill : missingSkills) {
                String encoded = skill.replace(" ", "+");
                fallback.put(skill, List.of(
                        Map.of("label", skill + " Tutorial",
                                "url", "https://www.youtube.com/results?search_query=" + encoded + "+tutorial",
                                "type", "youtube"),
                        Map.of("label", "Official Docs",
                                "url", "https://www.google.com/search?q=" + encoded + "+official+documentation",
                                "type", "docs"),
                        Map.of("label", "Roadmap.sh",
                                "url", "https://roadmap.sh",
                                "type", "roadmap"),
                        Map.of("label", "Practice on HackerRank",
                                "url", "https://www.hackerrank.com/dashboard",
                                "type", "practice")
                ));
            }
            return fallback;
        }
    }
}