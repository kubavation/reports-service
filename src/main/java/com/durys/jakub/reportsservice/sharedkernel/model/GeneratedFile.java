package com.durys.jakub.reportsservice.sharedkernel.model;


import com.durys.jakub.reportsservice.report.domain.ReportFormat;

public record GeneratedFile(byte[] file, String fileName, ReportFormat format) {}
