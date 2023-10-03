package com.durys.jakub.reportsservice.report.infrastructure.in.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreationParam {
    private String name;
    private Object value;
}
