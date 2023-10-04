package com.durys.jakub.reportsservice.pattern.filestorage;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface FilePatternRepository {
    void store(ReportPattern pattern, MultipartFile file);
    File load(ReportPattern pattern);
    Path path(ReportPattern pattern);
}
