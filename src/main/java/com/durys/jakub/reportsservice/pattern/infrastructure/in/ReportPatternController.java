package com.durys.jakub.reportsservice.pattern.infrastructure.in;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/report-patterns")
@RequiredArgsConstructor
public class ReportPatternController {

    private final ReportPatternRepository reportPatternRepository;

    @GetMapping("/subsystem/{subsystem}")
    public Set<ReportPatternInfo> patterns(@PathVariable String subsystem) {
        return reportPatternRepository.subsystemPatterns(subsystem);
    }

}
