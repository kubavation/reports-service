package com.durys.jakub.reportsservice.report.scheduling.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class ScheduledReport {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "REPORT_ID")
    private Long reportId;

    private LocalDateTime at;
}
