package com.durys.jakub.reportsservice.pattern.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ReportPattern {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String subsystem;
    private byte[] file;

}
