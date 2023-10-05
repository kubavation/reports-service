package com.durys.jakub.reportsservice.report.application.command;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.report.domain.command.DownloadReportCommand;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;
import lombok.RequiredArgsConstructor;

@CommandHandling
@RequiredArgsConstructor
public class DownloadReportCommandHandler implements CommandHandler<DownloadReportCommand, GeneratedFile> {

    private final ReportRepository reportRepository;

    @Override
    public GeneratedFile handle(DownloadReportCommand command) {
        return reportRepository.findById(command.reportId())
                .map(report -> new GeneratedFile(report.getFile(), report.getFileName(), report.getFormat()))
                .orElseThrow(RuntimeException::new);
    }
}
