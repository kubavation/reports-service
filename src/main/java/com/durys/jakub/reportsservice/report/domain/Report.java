package com.durys.jakub.reportsservice.report.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private byte[] file;
    
    @Embedded
    private ReportCreationStatus status;

}
