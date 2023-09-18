package com.durys.jakub.reportsservice.pattern.domain;

import com.durys.jakub.reportsservice.sharedkernel.model.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ReportPatternParameter {

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
}