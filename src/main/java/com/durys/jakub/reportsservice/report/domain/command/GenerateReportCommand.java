package com.durys.jakub.reportsservice.report.domain.command;

import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreationParam;
import com.durys.jakub.reportsservice.report.domain.ReportFormat;
import com.durys.jakub.reportsservice.cqrs.command.Command;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;

import java.util.Set;

public record GenerateReportCommand(String reportName, String subsystem, ReportFormat format, Set<ReportCreationParam> parameters,
                                    String title, String description) implements Command<GeneratedFile> {
}
