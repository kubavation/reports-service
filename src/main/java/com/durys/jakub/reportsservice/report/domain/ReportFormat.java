package com.durys.jakub.reportsservice.report.domain;

public enum ReportFormat {
    PDF("pdf");

    private final String format;

    ReportFormat(String format) {
        this.format = format;
    }

    public String format() {
        return format;
    }
}
