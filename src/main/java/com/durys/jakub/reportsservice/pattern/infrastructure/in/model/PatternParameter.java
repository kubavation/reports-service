package com.durys.jakub.reportsservice.pattern.infrastructure.in.model;

import lombok.Data;

@Data
public class PatternParameter {
    private String name;
    private String type;

    public PatternParameter(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
