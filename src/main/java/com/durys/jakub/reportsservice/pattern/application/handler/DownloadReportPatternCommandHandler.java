package com.durys.jakub.reportsservice.pattern.application.handler;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.command.DownloadReportPatternCommand;
import com.durys.jakub.reportsservice.pattern.filestorage.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;

@CommandHandling
@Slf4j
@RequiredArgsConstructor
public class DownloadReportPatternCommandHandler implements CommandHandler<DownloadReportPatternCommand, GeneratedFile> {

    private final ReportPatternRepository reportPatternRepository;
    private final FilePatternRepository filePatternRepository;

    @Override
    public GeneratedFile handle(DownloadReportPatternCommand command) {

        log.info("downloading file pattern (ID: {})", command.patternId());

        ReportPattern entity = reportPatternRepository.findById(command.patternId())
                .orElseThrow(RuntimeException::new);

        return new GeneratedFile(readFile(entity), entity.getPatternFile().getFileName(), null);
    }

    private byte[] readFile(ReportPattern reportPattern) {
        try {
            return Files.readAllBytes(filePatternRepository.path(reportPattern));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
