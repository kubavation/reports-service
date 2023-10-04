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

    private LocalDateTime at;

    public ScheduledReport(Long reportId, LocalDateTime at) {
        this.reportId = reportId;
        this.at = at;
    }

    public ScheduledReport markAsStarted() {
        this.status = ReportCreationStatus.valueOf(IN_PROGRESS, LocalDateTime.now());
        return this;
    }

}
