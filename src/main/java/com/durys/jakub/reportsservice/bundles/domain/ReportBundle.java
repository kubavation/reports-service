package com.durys.jakub.reportsservice.bundles.domain;

import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.sharedkernel.model.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "REPORT_BUNDLE")
@NoArgsConstructor
@Data
public class ReportBundle {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "BUNDLED_REPORT",
        joinColumns = {
            @JoinColumn(name = "BUNDLE_ID")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "REPORT_ID")
        }
    )
    private Set<Report> reports;

    @Enumerated(EnumType.STRING)
    private Status status;

}
