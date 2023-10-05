package com.durys.jakub.reportsservice.pattern.domain.command;

import com.durys.jakub.reportsservice.cqrs.command.Command;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;

public record DownloadReportPatternCommand(Long patternId) implements Command<GeneratedFile> {
}
