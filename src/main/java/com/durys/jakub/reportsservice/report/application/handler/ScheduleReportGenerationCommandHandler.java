package com.durys.jakub.reportsservice.report.application.handler;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.report.domain.command.ScheduleReportGenerationCommand;
import com.durys.jakub.reportsservice.scheduling.ReportScheduledGeneratorService;
import lombok.RequiredArgsConstructor;

@CommandHandling
@RequiredArgsConstructor
public class ScheduleReportGenerationCommandHandler implements CommandHandler<ScheduleReportGenerationCommand, Void> {

    private final ReportScheduledGeneratorService reportScheduledGeneratorService;

    @Override
    public Void handle(ScheduleReportGenerationCommand command) {
        reportScheduledGeneratorService.schedule(command.reportName(), command.subsystem(), command.parameters(),
                command.format(), command.title(), command.description(), command.at());
        return null;
    }
}
