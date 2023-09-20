package com.durys.jakub.reportsservice.pattern.domain;

import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import com.durys.jakub.reportsservice.sharedkernel.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "REP_REPORT_PATTERN")
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
