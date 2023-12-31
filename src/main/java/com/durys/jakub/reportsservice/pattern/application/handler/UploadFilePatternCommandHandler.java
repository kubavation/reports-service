package com.durys.jakub.reportsservice.pattern.application.handler;

import com.durys.jakub.reportsservice.common.transaction.Tx;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.command.UploadFilePatternCommand;
import com.durys.jakub.reportsservice.pattern.filestorage.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CommandHandling
@RequiredArgsConstructor
public class UploadFilePatternCommandHandler implements CommandHandler<UploadFilePatternCommand, Void> {

    private final ReportPatternRepository reportPatternRepository;
    private final FilePatternRepository filePatternRepository;
    private final Tx tx;

    @Override
    public Void handle(UploadFilePatternCommand command) {

        return tx.execute(status -> {

            log.info("uploading file pattern (ID: {})", command.patternId());

            ReportPattern entity = reportPatternRepository.findById(command.patternId())
                    .map(pattern -> pattern.withPatternFile(command.file()))
                    .map(reportPatternRepository::save)
                    .orElseThrow(RuntimeException::new);

            filePatternRepository.store(entity, command.file());

            return null;
        });

    }
}
