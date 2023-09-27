package com.durys.jakub.reportsservice.pattern.domain;

public interface FilePatternRepository {
    void store(String subsystem, String fileName, byte[] content);
}
