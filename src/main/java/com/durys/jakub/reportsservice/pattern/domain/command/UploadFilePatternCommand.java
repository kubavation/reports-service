package com.durys.jakub.reportsservice.pattern.domain.command;

import com.durys.jakub.reportsservice.cqrs.command.Command;
import org.springframework.web.multipart.MultipartFile;

public record UploadFilePatternCommand(Long patternId, MultipartFile file) implements Command<Void> {
}
