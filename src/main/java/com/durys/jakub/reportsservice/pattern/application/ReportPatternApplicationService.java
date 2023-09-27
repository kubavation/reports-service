package com.durys.jakub.reportsservice.pattern.application;

import com.durys.jakub.reportsservice.pattern.domain.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.domain.PatternFile;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternParameter;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternDTO;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportPatternApplicationService {

    private final ReportPatternRepository patternRepository;
    private final FilePatternRepository filePatternRepository;

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

    @Transactional
    public void create(ReportPatternDTO pattern, MultipartFile file) throws IOException {

        log.info("creating pattern");

        if (log.isDebugEnabled()) {
            log.debug("pattern {}", pattern);
        }

        ReportPattern reportPattern = patternRepository.save(PatternConverter.convert(pattern, file));

        Set<ReportPatternParameter> parameters = PatternConverter.convert(reportPattern, pattern.getParameters());

        reportPattern.setParameters(parameters);

        patternRepository.save(reportPattern);
    }

    @Transactional
    public void edit(Long patternId, ReportPatternDTO pattern, MultipartFile file) throws IOException {

        log.info("edit pattern (ID: {})", patternId);

        ReportPattern entity = patternRepository.findById(patternId)
                .orElseThrow(RuntimeException::new);

        ReportPattern reportPattern = PatternConverter.convert(pattern, file);
        reportPattern.setId(entity.getId());

        patternRepository.save(reportPattern);
    }

    public void delete(Long patternId) {

        log.info("delete pattern (ID: {})", patternId);

        ReportPattern entity = patternRepository.findById(patternId)
                .orElseThrow(RuntimeException::new);

        entity.markAsDeleted();
        patternRepository.save(entity);
    }

    @Transactional
    public void upload(Long patternId, MultipartFile file) throws IOException {

        log.info("uploading file pattern (ID: {})", patternId);

        ReportPattern entity = patternRepository.findById(patternId)
                .orElseThrow(RuntimeException::new);

        entity.setPatternFile(new PatternFile(file.getBytes(), file.getOriginalFilename()));

        patternRepository.save(entity);

        filePatternRepository.store(entity, file);
    }

    public PatternFile download(Long patternId) {

        log.info("downloading file pattern (ID: {})", patternId);

        ReportPattern entity = patternRepository.findById(patternId)
                .orElseThrow(RuntimeException::new);

        return entity.getPatternFile();
    }
}
