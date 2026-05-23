package com.ats.atsbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ResumeService {

    private final String UPLOAD_DIR =
            "uploads/";

    public String uploadResume(
            MultipartFile file
    ) throws IOException {

        File directory = new File(UPLOAD_DIR);

        if (!directory.exists()) {
            directory.mkdir();
        }

        String filePath =
                UPLOAD_DIR + file.getOriginalFilename();

        file.transferTo(new File(filePath));

        return "Resume uploaded successfully";
    }
}