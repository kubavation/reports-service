package com.durys.jakub.reportsservice.pattern.infrastructure.in;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.pattern.infrastructure.ReportPatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameterDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternDTO;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/report-patterns")
@RequiredArgsConstructor
public class ReportPatternController {

    private final ReportPatternRepository reportPatternRepository;
    private final ReportPatternApplicationService reportPatternApplicationService;

    @Operation(description = "List of patterns based on subsystem")
    @ApiResponse(responseCode = "200", description = "List of patterns")
    @GetMapping("/subsystem/{subsystem}")
    public Set<ReportPatternInfo> patterns(@Parameter(description ="Subsystem") @PathVariable String subsystem) {
        log.info("Finding patterns for subsystem {}", subsystem);
        return reportPatternRepository.subsystemPatterns(subsystem);
    }

    @Operation(description = "List of pattern parameters")
    @ApiResponse(responseCode = "200", description = "List of parameters")
    @GetMapping("/{patternId}/parameters")
    public Set<PatternParameterDTO> patternParameters(@Parameter(description ="Pattern ID") @PathVariable Long patternId) {
        return reportPatternRepository.patternParams(patternId);
    }

    @Operation(description = "Create report pattern")
    @ApiResponse(responseCode = "200", description = "Report pattern successfully created")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createPattern(@Parameter(description = "Pattern definition") @RequestPart ReportPatternDTO pattern,
                              @Parameter(description = "Pattern file") @RequestPart MultipartFile file) throws Exception {
        reportPatternApplicationService.create(pattern, file);
    }

    @PutMapping(path = "/{patternId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void editPattern(@Parameter(description = "Pattern ID")  @PathVariable Long patternId,
                            @Parameter(description = "Pattern definition") @RequestPart ReportPatternDTO pattern,
                            @Parameter(description = "Pattern file") @RequestPart MultipartFile file) throws Exception {
        reportPatternApplicationService.edit(patternId, pattern, file);
    }

    @DeleteMapping("/{patternId}")
    public void deletePattern(@Parameter(description = "Pattern ID")  @PathVariable Long patternId) {
        reportPatternApplicationService.delete(patternId);
    }
}
