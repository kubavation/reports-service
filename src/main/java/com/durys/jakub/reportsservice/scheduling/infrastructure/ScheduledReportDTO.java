package com.durys.jakub.reportsservice.scheduling.infrastructure;

import com.durys.jakub.reportsservice.report.domain.ReportCreationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduledReportDTO {
    private Long id;
    private String name;
    private String description;
    private String subsystem;
    private String fileName;
    private ReportCreationStatus status;
    private LocalDateTime at;

    public ScheduledReportDTO(Long id, String name, String description, String subsystem, String fileName, ReportCreationStatus status, LocalDateTime at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subsystem = subsystem;
        this.fileName = fileName;
        this.status = status;
        this.at = at;
    }
}
