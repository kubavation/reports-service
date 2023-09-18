package com.durys.jakub.reportsservice.report.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class ReportParameter {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Object value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_id")
    private Report report;

    public ReportParameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
