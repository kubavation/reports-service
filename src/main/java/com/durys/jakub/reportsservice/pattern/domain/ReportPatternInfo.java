package com.durys.jakub.reportsservice.pattern.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
class ReportPatternInfo {

    @Column(name = "PATTERN_NAME")
    private String name;
    @Column(name = "PATTERN_DESCRIPTION")
    private String description;
    @Column(name = "PATTER_SUBSYSTEM")
    private String subsystem;

    @Column(name = "GENERATION_TYPE")
    @Enumerated(EnumType.STRING)
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
