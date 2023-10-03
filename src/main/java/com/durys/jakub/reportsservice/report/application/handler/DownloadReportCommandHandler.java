package com.durys.jakub.reportsservice.report.application.handler;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.report.command.DownloadReportCommand;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import lombok.RequiredArgsConstructor;

@CommandHandling
@RequiredArgsConstructor
public class DownloadReportCommandHandler implements CommandHandler<DownloadReportCommand, GeneratedReport> {

    private final ReportRepository reportRepository;

    @Override
    public GeneratedReport handle(DownloadReportCommand command) {
        return reportRepository.findById(command.reportId())
                .map(report -> new GeneratedReport(report.getFile(), report.getFileName(), report.getFormat()))
                .orElseThrow(RuntimeException::new);
    }
}
