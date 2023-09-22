package com.durys.jakub.reportsservice.pattern.infrastructure.in.model;

import lombok.Data;

@Data
public class ReportPatternInfoDTO {
    private Long id;
    private String name;
    private String description;
    private String subsystem;
    private String fileName;

    public ReportPatternInfoDTO(Long id, String name, String description, String subsystem, String fileName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subsystem = subsystem;
        this.fileName = fileName;
    }
}
