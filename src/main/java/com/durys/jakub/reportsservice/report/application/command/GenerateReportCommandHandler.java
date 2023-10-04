package com.durys.jakub.reportsservice.report.application.command;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.common.generator.ReportGenerator;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.report.domain.command.GenerateReportCommand;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import lombok.RequiredArgsConstructor;

@CommandHandling
@RequiredArgsConstructor
public class GenerateReportCommandHandler implements CommandHandler<GenerateReportCommand, GeneratedReport> {

    private final ReportGenerator reportGenerator;
    private final ReportPatternRepository reportPatternRepository;

    @Override
    public GeneratedReport handle(GenerateReportCommand command) {

        ReportPattern reportPattern = reportPatternRepository.findBy(command.subsystem(), command.reportName())
                .orElseThrow(RuntimeException::new);

        return reportGenerator.generate(reportPattern, command.format(), command.parameters());
    }
}
