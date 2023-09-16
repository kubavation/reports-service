package com.durys.jakub.reportsservice.report.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private byte[] file;

    @Embedded
    private ReportCreationStatus status;


    public static Report instance() {
        return new Report().withStatus(new ReportCreationStatus(ReportCreationStatus.Status.NEW, LocalDateTime.now()));
    }

    public Report withStatus(ReportCreationStatus status) {
        this.status = status;
        return this;
    }

}
