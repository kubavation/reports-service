package com.durys.jakub.reportsservice.pattern.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportPatternInfo {
    private String name;
    private String description;
    private String subsystem;
}
