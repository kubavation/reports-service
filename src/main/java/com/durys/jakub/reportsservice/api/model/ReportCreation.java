package com.durys.jakub.reportsservice.api.model;

import lombok.Data;

import java.util.Set;

@Data
public class ReportCreation {
    private String reportName;
    private String subsystem;
    private ReportFormat format;
    private Set<ReportCreationParam> parameters;
    private String title;
    private String description;
}
