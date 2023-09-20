package com.durys.jakub.reportsservice.pattern.application;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternDTO;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportPatternApplicationService {

    private final ReportPatternRepository patternRepository;

    public InputStream filePattern(String name, String subsystem) {

        log.info("loading report pattern of name: {} and subsystem: {}", name, subsystem);

        byte[] bytes = patternRepository.filePatternOf(name, subsystem)
                .orElseThrow(RuntimeException::new);

        return new ByteArrayInputStream(bytes);
    }

    public ReportPatternInfo reportPatternInfo(String name, String subsystem) {
        return patternRepository.patternInformations(name, subsystem)
                .orElseThrow(RuntimeException::new);
    }

    public void create(ReportPatternDTO pattern, MultipartFile file) throws IOException {

        log.info("creating pattern");

        if (log.isDebugEnabled()) {
            log.debug("pattern {}", pattern);
        }

        ReportPattern reportPattern = PatternConverter.convert(pattern, file);

        patternRepository.save(reportPattern);
    }
}
