package com.durys.jakub.reportsservice.sharedkernel.model;

import com.durys.jakub.reportsservice.pattern.domain.ReportPatternGenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class ReportPatternInfo {
    private String name;
    private String description;
    private String subsystem;

    @Column(name = "GENERATION_TYPE")
    private ReportPatternGenerationType generationType;

    public ReportPatternInfo(String name, String description, String subsystem) {
        this.name = name;
        this.description = description;
        this.subsystem = subsystem;
        this.generationType = ReportPatternGenerationType.NONE;
    }

    public ReportPatternInfo(String name, String description, String subsystem, ReportPatternGenerationType generationType) {
        this.name = name;
        this.description = description;
        this.subsystem = subsystem;
        this.generationType = generationType;
    }

    public boolean dbGeneration() {
        return ReportPatternGenerationType.DB.equals(generationType);
    }
}
