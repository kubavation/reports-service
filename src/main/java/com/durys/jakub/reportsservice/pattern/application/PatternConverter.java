package com.durys.jakub.reportsservice.pattern.application;

import com.durys.jakub.reportsservice.pattern.domain.PatternFile;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternParameter;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameterDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternDTO;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import com.durys.jakub.reportsservice.sharedkernel.model.Status;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class PatternConverter {

    static ReportPattern convert(ReportPatternDTO dto, MultipartFile file) throws IOException {
        return ReportPattern.builder()
                .informations(new ReportPatternInfo(dto.getName(), dto.getDescription(), dto.getSubsystem()))
                .patternFile(new PatternFile(file.getBytes(), file.getOriginalFilename()))
                .parameters(convert(dto.getParameters()))
                .status(Status.ACTIVE)
                .build();
    }

    private static ReportPatternParameter convert(PatternParameterDTO dto) {
        return new ReportPatternParameter(dto.getName(), dto.getType());
    }

    private static Set<ReportPatternParameter> convert(Set<PatternParameterDTO> dtos) {
        return dtos.stream()
                .map(PatternConverter::convert)
                .collect(Collectors.toSet());
    }

}
