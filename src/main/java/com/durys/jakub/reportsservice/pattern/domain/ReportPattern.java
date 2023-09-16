package com.durys.jakub.reportsservice.pattern.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ReportPattern {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ReportPatternInfo informations;
    private byte[] file;

}
