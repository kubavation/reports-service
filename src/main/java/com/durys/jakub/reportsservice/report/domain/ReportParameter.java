package com.durys.jakub.reportsservice.report.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "REP_REPORT_PARAM")
public class ReportParameter {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_id")
    private Report report;

    public ReportParameter(String name, Object value) {
        this.name = name;
        this.value = value.toString();
    }
}
