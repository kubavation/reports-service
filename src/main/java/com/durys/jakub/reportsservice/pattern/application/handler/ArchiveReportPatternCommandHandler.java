package com.durys.jakub.reportsservice.pattern.application.handler;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.command.ArchiveReportPatternCommand;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CommandHandling
@Slf4j
@RequiredArgsConstructor
public class ArchiveReportPatternCommandHandler implements CommandHandler<ArchiveReportPatternCommand, Void> {

    private final ReportPatternRepository reportPatternRepository;

    @Override
    public Void handle(ArchiveReportPatternCommand command) {

        log.info("delete pattern (ID: {})", command.patternId());

        ReportPattern entity = reportPatternRepository.findById(command.patternId())
                .map(ReportPattern::markAsDeleted)
                .orElseThrow(RuntimeException::new);

        reportPatternRepository.save(entity);
        return null;
    }
}
