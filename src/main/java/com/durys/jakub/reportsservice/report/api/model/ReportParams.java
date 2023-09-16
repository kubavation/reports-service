package com.durys.jakub.reportsservice.report.api.model;

import lombok.Data;

import java.util.Map;

@Data
public class ReportParams {
    private Map<String, Object> value;
}
