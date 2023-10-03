package com.durys.jakub.reportsservice.report.command;

import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreationParam;
import com.durys.jakub.reportsservice.report.domain.ReportFormat;
import com.durys.jakub.reportsservice.cqrs.command.Command;

import java.time.LocalDateTime;
import java.util.Set;

public record ScheduleReportGenerationCommand(String reportName, String subsystem, ReportFormat format,
                                              Set<ReportCreationParam> parameters,
                                              String title, String description, LocalDateTime at) implements Command<Void> {
}
