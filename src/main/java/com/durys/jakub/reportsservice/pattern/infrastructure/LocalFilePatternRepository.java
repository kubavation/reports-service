package com.durys.jakub.reportsservice.pattern.infrastructure;

import com.durys.jakub.reportsservice.pattern.domain.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.domain.PatternFile;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFilePatternRepository implements FilePatternRepository {

    private static final String REPORTS_SPACE = "C:\\Users\\48533\\projects\\reports";

    @Override
    public void store(ReportPattern pattern, MultipartFile file) {

        try {
            Files.write(Path.of(REPORTS_SPACE, pattern.getInformations().getSubsystem(), file.getOriginalFilename()), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
