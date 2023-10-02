package com.durys.jakub.reportsservice.bundles.infrastructure.query.model;

public record ReportInBundleDTO(Long id, String fileName, String title, String description,
                                String patternName, String patternDescription, String patternSubsystem) {
}
