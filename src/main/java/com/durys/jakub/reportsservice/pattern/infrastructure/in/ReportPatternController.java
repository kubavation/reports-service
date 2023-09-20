package com.durys.jakub.reportsservice.pattern.infrastructure.in;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameter;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
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

    @Operation(description = "List of patterns based on subsystem")
    @ApiResponse(responseCode = "200", description = "List of patterns")
    @GetMapping("/subsystem/{subsystem}")
    public Set<ReportPatternInfo> patterns(@Param("Subsystem") @PathVariable String subsystem) {
        return reportPatternRepository.subsystemPatterns(subsystem);
    }

    @Operation(description = "List of pattern parameters")
    @ApiResponse(responseCode = "200", description = "List of parameters")
    @GetMapping("/{patternId}/parameters")
    public Set<PatternParameter> patternParameters(@Param("Pattern ID") @PathVariable Long patternId) {
        return reportPatternRepository.patternParams(patternId);
    }
}
