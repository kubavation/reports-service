package com.durys.jakub.reportsservice.pattern.domain;

import com.durys.jakub.reportsservice.sharedkernel.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "REP_REPORT_PATTERN_PARAM")
class ReportPatternParameter {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_id")
    private ReportPattern pattern;

    @Enumerated(EnumType.STRING)
    private Status status;

    public ReportPatternParameter(String name, String type) {
        this.name = name;
        this.type = type;
        this.status = Status.ACTIVE;
    }

    public ReportPatternParameter(ReportPattern pattern, String name, String type) {
        this(name, type);
        this.pattern = pattern;
    }
}
