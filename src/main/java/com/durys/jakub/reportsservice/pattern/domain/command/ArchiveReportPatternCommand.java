package com.durys.jakub.reportsservice.pattern.domain.command;

import com.durys.jakub.reportsservice.cqrs.command.Command;

public record ArchiveReportPatternCommand(Long patternId) implements Command<Void> {
}
