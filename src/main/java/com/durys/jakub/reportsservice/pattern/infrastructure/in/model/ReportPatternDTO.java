package com.durys.jakub.reportsservice.pattern.infrastructure.in.model;

import lombok.Data;

import java.util.Set;

@Data
public class ReportPatternDTO {
    private String name;
    private String subsystem;
    private Set<PatternParameterDTO> parameters;
}
