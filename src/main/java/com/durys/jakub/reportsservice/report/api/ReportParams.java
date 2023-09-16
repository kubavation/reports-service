package com.durys.jakub.reportsservice.report.api;

import lombok.Data;

import java.util.Map;

@Data
public class ReportParams {
    private Map<String, Object> value;
}
