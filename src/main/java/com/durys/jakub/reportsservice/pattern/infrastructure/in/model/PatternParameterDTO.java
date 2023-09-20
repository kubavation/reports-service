package com.durys.jakub.reportsservice.pattern.infrastructure.in.model;

import lombok.Data;

@Data
public class PatternParameterDTO {
    private String name;
    private String type;

    public PatternParameterDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
