package com.durys.jakub.reportsservice.sharedkernel.model;


import com.durys.jakub.reportsservice.report.domain.ReportFormat;

public record GeneratedReport(byte[] file, String fileName, ReportFormat format) {}
