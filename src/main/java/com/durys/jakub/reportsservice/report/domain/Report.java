package com.durys.jakub.reportsservice.report.domain;

import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReportParameter> parameters;


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
