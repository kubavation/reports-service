package com.durys.jakub.reportsservice.report.domain;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
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

    @Embedded
    private ReportPatternInfo patternInformations;
    private String format;

    @Embedded
    private ReportCreationStatus status;


    public static Report instanceOf(ReportPatternInfo patternInformations, String format) {
        return Report.builder()
                .patternInformations(patternInformations)
                .format(format)
                .status(new ReportCreationStatus(ReportCreationStatus.Status.IN_PROGRESS, LocalDateTime.now()))
                .build();
    }


    public Report markAsFailed() {
        this.status = new ReportCreationStatus(ReportCreationStatus.Status.FAILURE, LocalDateTime.now());
        return this;
    }

    public Report markAsSucceeded() {
        this.status = new ReportCreationStatus(ReportCreationStatus.Status.SUCCESS, LocalDateTime.now());
        return this;
    }

    public Report with(String fileName, byte[] file) {
        this.file = file;
        this.fileName = fileName;
        return this;
    }

}
