package com.durys.jakub.reportsservice.report.domain;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundle;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REP_REPORT")
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private byte[] file;

    @Embedded
    private ReportPatternInfo patternInformations;

    @Enumerated(EnumType.STRING)
    private ReportFormat format;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReportParameter> parameters;

    @Column(name = "TENANT_ID")
    private UUID tenantId;

    private String title;

    private String description;

    @ManyToMany(mappedBy = "reports")
    private Set<ReportBundle> bundles;

    public static Report instanceOf(ReportPattern pattern, ReportFormat format, UUID author, String title, String description) {
        return Report.builder()
                .patternInformations(pattern.getInformations())
                .format(format)
                .tenantId(author)
                .title(title)
                .description(description)
                .build();
    }

    public Report withParameters(Set<ReportParameter> parameters) {
        this.parameters = parameters;
        return this;
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
