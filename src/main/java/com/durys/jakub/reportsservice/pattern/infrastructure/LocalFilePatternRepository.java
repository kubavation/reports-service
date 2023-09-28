package com.durys.jakub.reportsservice.pattern.infrastructure;

import com.durys.jakub.reportsservice.pattern.domain.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFilePatternRepository implements FilePatternRepository {

    private static final String REPORTS_SPACE = "C:\\Users\\48533\\projects\\reports";

    @Override
    public void store(ReportPattern pattern, MultipartFile file) {

        try {

            Path subsystemPath = Path.of(REPORTS_SPACE, pattern.getInformations().getSubsystem());

            if (!Files.exists(subsystemPath)) {
                Files.createDirectories(subsystemPath);
            }

            Files.write(Path.of(REPORTS_SPACE,
                    pattern.getInformations().getSubsystem(),
                    pattern.getInformations().getName()), file.getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File load(ReportPattern pattern) {
        try {
            return path(pattern).toFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path path(ReportPattern pattern) {
        return Path.of(REPORTS_SPACE, pattern.getInformations().getSubsystem(), pattern.getPatternFile().getFileName());
    }
}
