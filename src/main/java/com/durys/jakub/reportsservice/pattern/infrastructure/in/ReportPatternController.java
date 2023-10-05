package com.durys.jakub.reportsservice.pattern.infrastructure.in;

import com.durys.jakub.reportsservice.common.model.OperationResult;
import com.durys.jakub.reportsservice.cqrs.command.CommandGateway;
import com.durys.jakub.reportsservice.cqrs.query.Queries;
import com.durys.jakub.reportsservice.pattern.domain.command.ArchiveReportPatternCommand;
import com.durys.jakub.reportsservice.pattern.domain.command.CreateReportPatternCommand;
import com.durys.jakub.reportsservice.pattern.domain.command.DownloadReportPatternCommand;
import com.durys.jakub.reportsservice.pattern.domain.command.UploadFilePatternCommand;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameterDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternInfoDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.query.FindSubsystemReportPatternsQuery;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/report-patterns")
@RequiredArgsConstructor
public class ReportPatternController {

    private final ReportPatternRepository reportPatternRepository;
    private final CommandGateway commandGateway;
    private final Queries queries;

    @Operation(description = "List of patterns based on subsystem")
    @ApiResponse(responseCode = "200", description = "List of patterns")
    @GetMapping("/subsystem/{subsystem}")
    public Set<ReportPatternInfoDTO> patterns(@Parameter(description ="Subsystem") @PathVariable String subsystem) {
        return queries.find(
                new FindSubsystemReportPatternsQuery(subsystem));
    }

    @Operation(description = "List of pattern parameters")
    @ApiResponse(responseCode = "200", description = "List of parameters")
    @GetMapping("/{patternId}/parameters")
    public Set<PatternParameterDTO> patternParameters(@Parameter(description ="Pattern ID") @PathVariable Long patternId) {
        return reportPatternRepository.patternParams(patternId);
    }

    @Operation(description = "Upload pattern file")
    @ApiResponse(responseCode = "200", description = "File uploaded")
    @PatchMapping("/{patternId}/files")
    public void uploadFilePattern(@Parameter(description ="Pattern ID") @PathVariable Long patternId,
                                  @RequestParam MultipartFile file) throws Exception {
        commandGateway.dispatch(
                new UploadFilePatternCommand(patternId, file));
    }

    @Operation(description = "Upload pattern file")
    @ApiResponse(responseCode = "200", description = "File uploaded")
    @GetMapping("/{patternId}/files")
    public ResponseEntity<Resource> downloadFilePattern(@Parameter(description ="Pattern ID") @PathVariable Long patternId) throws Exception {

        GeneratedFile generated = commandGateway.dispatch(
                new DownloadReportPatternCommand(patternId));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, generated.fileName());
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(generated.file().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(generated.file()));
    }

    @Operation(description = "Create report pattern")
    @ApiResponse(responseCode = "200", description = "Report pattern successfully created")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public OperationResult createPattern(@Parameter(description = "Pattern definition") @RequestPart ReportPatternDTO pattern,
                              @Parameter(description = "Pattern file") @RequestPart MultipartFile file) throws Exception {

        return commandGateway.dispatch(
                new CreateReportPatternCommand(
                        pattern.getName(), pattern.getDescription(), pattern.getSubsystem(),
                        pattern.getGenerationType(), pattern.getParameters(), file));
    }

//    @PutMapping(path = "/{patternId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public void editPattern(@Parameter(description = "Pattern ID")  @PathVariable Long patternId,
//                            @Parameter(description = "Pattern definition") @RequestPart ReportPatternDTO pattern,
//                            @Parameter(description = "Pattern file") @RequestPart MultipartFile file) throws Exception {
//        reportPatternApplicationService.edit(patternId, pattern, file);
//    }

    @DeleteMapping("/{patternId}")
    public void deletePattern(@Parameter(description = "Pattern ID")  @PathVariable Long patternId) {
        commandGateway.dispatch(
                new ArchiveReportPatternCommand(patternId));
    }
}
