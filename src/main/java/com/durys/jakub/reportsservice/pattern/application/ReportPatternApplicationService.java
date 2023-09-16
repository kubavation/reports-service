package com.durys.jakub.reportsservice.pattern.application;

import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
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

}
