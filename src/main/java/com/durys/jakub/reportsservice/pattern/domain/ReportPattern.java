package com.durys.jakub.reportsservice.pattern.domain;

import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import com.durys.jakub.reportsservice.sharedkernel.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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

    @Embedded
    private PatternFile patternFile;

    @OneToMany(
            mappedBy = "pattern",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ReportPatternParameter> parameters = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    public void markAsDeleted() {
        this.status = Status.DELETED;
    }

    public ReportPattern withFile(byte[] content) {
        patternFile.setFile(content);
        return this;
    }

    public String name() {
        return informations.getName();
    }

    public String subsystem() {
        return informations.getSubsystem();
    }

    public String description() {
        return informations.getDescription();
    }

}
