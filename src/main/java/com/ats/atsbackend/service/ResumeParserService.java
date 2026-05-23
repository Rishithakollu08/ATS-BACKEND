package com.ats.atsbackend.service;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;

import java.io.InputStream;

@Service
public class ResumeParserService {

    public String extractText(MultipartFile file) throws Exception {

        AutoDetectParser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();

        InputStream stream = file.getInputStream();

        parser.parse(stream, handler, metadata);

        return handler.toString();
    }
}