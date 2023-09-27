package com.durys.jakub.reportsservice.pattern.infrastructure;

import com.durys.jakub.reportsservice.pattern.domain.FilePatternRepository;

public class LocalFilePatternRepository implements FilePatternRepository {

    @Override
    public void store(String subsystem, String fileName, byte[] content) {

    }
}
