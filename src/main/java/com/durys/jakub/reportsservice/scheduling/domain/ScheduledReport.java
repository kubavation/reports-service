package com.durys.jakub.reportsservice.scheduling.domain;

import com.durys.jakub.reportsservice.report.domain.ReportCreationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.durys.jakub.reportsservice.report.domain.ReportCreationStatus.Status.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "REP_SCHEDULED_REPORT")
public class ScheduledReport {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "REPORT_ID")
    private Long reportId;

    @Embedded
    private ReportCreationStatus status;

    @Column(name = "SCHEDULED_AT")
    private LocalDateTime scheduleAt;

    public ScheduledReport(Long reportId, LocalDateTime scheduleAt) {
        this.reportId = reportId;
        this.scheduleAt = scheduleAt;
    }

    public ScheduledReport markAsStarted() {
        this.status = ReportCreationStatus.valueOf(IN_PROGRESS, LocalDateTime.now());
        return this;
    }

    public ScheduledReport markAsGenerated() {
        this.status = ReportCreationStatus.valueOf(SUCCESS, LocalDateTime.now());
        return this;
    }

    public ScheduledReport markAsFailed() {
        this.status = ReportCreationStatus.valueOf(FAILURE, LocalDateTime.now());
        return this;
    }

}
