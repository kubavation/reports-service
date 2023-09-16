package com.durys.jakub.reportsservice.report.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private byte[] file;

    private String reportName;
    private String subsystem;

    @Embedded
    private ReportCreationStatus status;


    public static Report instance(String reportName, String subsystem) {
        return Report.builder()
                .reportName(reportName)
                .subsystem(subsystem)
                .status(new ReportCreationStatus(ReportCreationStatus.Status.NEW, LocalDateTime.now()))
                .build();
    }

    public Report withStatus(ReportCreationStatus status) {
        this.status = status;
        return this;
    }

}
