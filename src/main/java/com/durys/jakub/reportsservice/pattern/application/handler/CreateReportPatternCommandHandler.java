package com.durys.jakub.reportsservice.pattern.application.handler;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.command.CreateReportPatternCommand;
import com.durys.jakub.reportsservice.pattern.filestorage.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CommandHandling
@RequiredArgsConstructor
public class CreateReportPatternCommandHandler implements CommandHandler<CreateReportPatternCommand, Void> {

    private final ReportPatternRepository reportPatternRepository;
    private final FilePatternRepository filePatternRepository;

    @Override
    @Transactional
    public Void handle(CreateReportPatternCommand command) {

        log.info("creating report pattern");

        ReportPattern reportPattern =
                reportPatternRepository.save(
                        new ReportPattern(command.name(), command.description(), command.subsystem(), command.file()));

        command.parameters()
                .forEach(parameter -> reportPattern.addParameter(parameter.getName(), parameter.getType()));

        ReportPattern saved = reportPatternRepository.save(reportPattern);

        filePatternRepository.store(saved, command.file());

        return null;
    }
}
