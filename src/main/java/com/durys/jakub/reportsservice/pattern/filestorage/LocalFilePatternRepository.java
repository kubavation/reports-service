package com.durys.jakub.reportsservice.pattern.filestorage;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFilePatternRepository implements FilePatternRepository {

    @Value("${reports.export.space}")
    private String reportsSpace;

    @Override
    public void store(ReportPattern pattern, MultipartFile file) {

        try {

            Path subsystemPath = Path.of(reportsSpace, pattern.getInformations().getSubsystem());

            if (!Files.exists(subsystemPath)) {
                Files.createDirectories(subsystemPath);
            }

            Path reportPath = Path.of(reportsSpace, pattern.getInformations().getSubsystem(), pattern.getInformations().getName());

            if (!Files.exists(reportPath)) {
                Files.createDirectories(reportPath);
            }

            Files.write(Path.of(
                    reportsSpace, pattern.getInformations().getSubsystem(),
                    pattern.getInformations().getName(), file.getOriginalFilename()), file.getBytes());

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
        return Path.of(reportsSpace,
                pattern.getInformations().getSubsystem(),
                pattern.getInformations().getName(),
                pattern.getPatternFile().getFileName());
    }
}
