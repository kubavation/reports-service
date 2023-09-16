package com.durys.jakub.reportsservice.report.scheduling.event;

import com.durys.jakub.reportsservice.event.Event;
import com.durys.jakub.reportsservice.report.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.api.model.ReportParams;

public record ScheduleReportGenerationEvent(Long reportId, String reportName, String subsystem,
                                            ReportParams params, ReportFormat format) implements Event {
}
