package com.durys.jakub.reportsservice.report.domain.command;

import com.durys.jakub.reportsservice.cqrs.command.Command;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;

public record DownloadReportCommand(Long reportId) implements Command<GeneratedReport> {
}
