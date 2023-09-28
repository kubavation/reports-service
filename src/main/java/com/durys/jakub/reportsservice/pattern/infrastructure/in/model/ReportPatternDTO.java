package com.durys.jakub.reportsservice.pattern.infrastructure.in.model;

import com.durys.jakub.reportsservice.pattern.domain.ReportPatternGenerationType;
import lombok.Data;

import java.util.Set;

@Data
public class ReportPatternDTO {
    private String name;
    private String description;
    private String subsystem;
    private ReportPatternGenerationType generationType;
    private Set<PatternParameterDTO> parameters;
}
