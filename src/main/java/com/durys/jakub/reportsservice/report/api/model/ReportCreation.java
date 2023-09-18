package com.durys.jakub.reportsservice.report.api.model;

import lombok.Data;

import java.util.Map;

@Data
public class ReportCreation {
    private String reportName;
    private String subsystem;
    private ReportFormat format;
    private Map<String, Object> parameters;
}
