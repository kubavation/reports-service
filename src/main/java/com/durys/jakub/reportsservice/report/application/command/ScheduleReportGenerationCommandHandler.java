package com.durys.jakub.reportsservice.report.application.command;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.report.domain.command.ScheduleReportGenerationCommand;
import com.durys.jakub.reportsservice.scheduling.application.ReportScheduledGeneratorService;
import lombok.RequiredArgsConstructor;

@CommandHandling
@RequiredArgsConstructor
public class ScheduleReportGenerationCommandHandler implements CommandHandler<ScheduleReportGenerationCommand, Void> {

    private final ReportScheduledGeneratorService reportScheduledGeneratorService;
    private final ReportPatternRepository reportPatternRepository;

    @Override
    public Void handle(ScheduleReportGenerationCommand command) {

        ReportPattern reportPattern = reportPatternRepository.findBy(command.subsystem(), command.reportName())
                .orElseThrow(RuntimeException::new);

        reportScheduledGeneratorService.schedule(reportPattern, command.parameters(), command.format(),
                command.title(), command.description(), command.at());

        return null;
    }
}
