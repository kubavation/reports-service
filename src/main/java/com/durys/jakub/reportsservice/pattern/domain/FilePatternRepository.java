package com.durys.jakub.reportsservice.pattern.domain;

import org.springframework.web.multipart.MultipartFile;

public interface FilePatternRepository {
    void store(ReportPattern pattern, MultipartFile file);
    byte[] load(ReportPattern pattern);
}
