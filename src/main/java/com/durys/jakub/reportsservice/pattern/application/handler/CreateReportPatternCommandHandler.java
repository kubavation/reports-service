package com.durys.jakub.reportsservice.pattern.application.handler;

import com.durys.jakub.reportsservice.common.exception.ValidationHandlers;
import com.durys.jakub.reportsservice.common.model.OperationResult;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo;
import com.durys.jakub.reportsservice.pattern.domain.command.CreateReportPatternCommand;
import com.durys.jakub.reportsservice.pattern.filestorage.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CommandHandling
@RequiredArgsConstructor
public class CreateReportPatternCommandHandler implements CommandHandler<CreateReportPatternCommand, OperationResult> {

    private final ReportPatternRepository reportPatternRepository;
    private final FilePatternRepository filePatternRepository;

    @Override
    @Transactional
    public OperationResult handle(CreateReportPatternCommand command) {

        log.info("creating report pattern");

        var handler = ValidationHandlers.aggregatingValidationExceptionHandler();

        ReportPatternInfo.test(command.name(), command.description(), command.subsystem(), handler);

        if (handler.hasErrors()) {
            return OperationResult.failure(handler.errorMessages());
        }

        ReportPattern reportPattern = reportPatternRepository
                .save(new ReportPattern(
                        new ReportPatternInfo(command.name(), command.description(), command.subsystem()), command.file()));

        command.parameters()
                .forEach(parameter -> reportPattern.addParameter(parameter.getName(), parameter.getType()));

        ReportPattern saved = reportPatternRepository.save(reportPattern);

        filePatternRepository.store(saved, command.file());

        return OperationResult.success();
    }
}
