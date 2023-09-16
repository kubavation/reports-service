package com.durys.jakub.reportsservice.pattern.domain;

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

    public ReportPatternInfo(String name, String description, String subsystem) {
        this.name = name;
        this.description = description;
        this.subsystem = subsystem;
    }
}
