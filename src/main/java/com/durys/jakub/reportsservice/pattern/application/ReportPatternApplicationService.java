package com.durys.jakub.reportsservice.pattern.application;

import com.durys.jakub.reportsservice.pattern.filestorage.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.domain.PatternFile;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternParameter;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportPatternApplicationService {

    private final ReportPatternRepository patternRepository;
    private final FilePatternRepository filePatternRepository;

    @Transactional
    public void edit(Long                                                                                                                                                                             , ReportPatternDTO pattern, MultipartFile file) throws IOException {

        log.info("edit pattern (ID: {})", patternId);

        ReportPattern entity = patternRepository.findById(patternId)
                .orElseThrow(RuntimeException::new);

        ReportPattern reportPattern = PatternConverter.convert(pattern, file);
        reportPattern.setId(entity.getId());

        patternRepository.save(reportPattern);
    }


    public PatternFile download(Long patternId) throws IOException {

        log.info("downloading file pattern (ID: {})", patternId);

        ReportPattern entity = patternRepository.findById(patternId)
                .orElseThrow(RuntimeException::new);

        return entity
                .withFile(Files.readAllBytes(filePatternRepository.path(entity)))
                .getPatternFile();
    }
}
