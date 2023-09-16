package com.durys.jakub.reportsservice.report.api.model;

public enum ReportFormat {
    PDF("pdf"),
    HTML("html");

    private final String format;

    ReportFormat(String format) {
        this.format = format;
    }

    public String format() {
        return format;
    }
}
