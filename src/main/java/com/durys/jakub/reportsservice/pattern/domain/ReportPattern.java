package com.durys.jakub.reportsservice.pattern.domain;

import lombok.Data;

@Data
public class ReportPattern {

    private Long id;
    private String name;
    private String description;
    private String subsystem;
    private byte[] file;

}
