package com.durys.jakub.reportsservice.report.infrastructure.in.model;

import com.durys.jakub.reportsservice.report.domain.ReportFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreation {
    private String reportName;
    private String subsystem;
    private ReportFormat format;
    private Set<ReportCreationParam> parameters;
    private String title;
    private String description;
}
