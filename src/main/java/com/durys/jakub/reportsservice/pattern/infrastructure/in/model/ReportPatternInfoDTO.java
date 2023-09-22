package com.durys.jakub.reportsservice.pattern.infrastructure.in.model;

import lombok.Data;

@Data
public class ReportPatternInfoDTO {
    private Long id;
    private String name;
    private String description;
    private String subsystem;

    public ReportPatternInfoDTO(Long id, String name, String description, String subsystem) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subsystem = subsystem;
    }
}
