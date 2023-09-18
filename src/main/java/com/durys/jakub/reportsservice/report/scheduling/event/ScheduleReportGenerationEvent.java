package com.durys.jakub.reportsservice.report.scheduling.event;

import com.durys.jakub.reportsservice.event.Event;
import com.durys.jakub.reportsservice.report.api.model.ReportFormat;

import java.time.LocalDateTime;
import java.util.Map;

public record ScheduleReportGenerationEvent(Long reportId, String reportName, String subsystem,
                                            Map<String, Object> params, ReportFormat format, LocalDateTime at) implements Event {
}
