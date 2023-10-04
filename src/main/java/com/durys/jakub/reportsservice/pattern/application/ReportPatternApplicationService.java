package com.durys.jakub.reportsservice.pattern.application;

import com.durys.jakub.reportsservice.pattern.filestorage.FilePatternRepository;
import com.durys.jakub.reportsservice.pattern.domain.PatternFile;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternParameter;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternDTO;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportPatternApplicationService {

    private final ReportPatternRepository patternRepository;
    private final FilePatternRepository filePatternRepository;

    @Transactional
    public void create(ReportPatternDTO pattern, MultipartFile file) throws IOException {

        log.info("creating pattern");

        if (log.isDebugEnabled()) {
            log.debug("pattern {}", pattern);
        }

        ReportPattern reportPattern = patternRepository.save(PatternConverter.convert(pattern, file));

        Set<ReportPatternParameter> parameters = PatternConverter.convert(reportPattern, pattern.getParameters());

        reportPattern.setParameters(parameters);

        ReportPattern saved = patternRepository.save(reportPattern);

        filePatternRepository.store(saved, file);
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
                .map(pattern -> pattern.patternFile(file))
                .map(patternRepository::save)
                .orElseThrow(RuntimeException::new);

        filePatternRepository.store(entity, file);
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
