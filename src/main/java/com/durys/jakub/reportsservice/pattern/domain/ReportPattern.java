package com.durys.jakub.reportsservice.pattern.domain;

import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import com.durys.jakub.reportsservice.sharedkernel.model.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class ReportPattern {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ReportPatternInfo informations;
    private byte[] file;

    @OneToMany(
            mappedBy = "pattern",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ReportPatternParameter> parameters;

    @Enumerated(EnumType.STRING)
    private Status status;

}
