package com.durys.jakub.reportsservice.pattern.domain.command;

import com.durys.jakub.reportsservice.common.model.OperationResult;
import com.durys.jakub.reportsservice.cqrs.command.Command;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternGenerationType;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameterDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public record CreateReportPatternCommand(String name, String description, String subsystem,
                                         ReportPatternGenerationType generationType,
                                         Set<PatternParameterDTO> parameters,
                                         MultipartFile file) implements Command<OperationResult> {
}
