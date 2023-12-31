package com.durys.jakub.reportsservice.report.infrastructure.in;

import com.durys.jakub.reportsservice.common.zip.Zip;
import com.durys.jakub.reportsservice.cqrs.command.CommandGateway;
import com.durys.jakub.reportsservice.report.domain.command.DownloadReportCommand;
import com.durys.jakub.reportsservice.report.domain.command.GenerateReportCommand;
import com.durys.jakub.reportsservice.report.domain.command.ScheduleReportGenerationCommand;
import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreation;
import com.durys.jakub.reportsservice.report.infrastructure.in.model.ScheduleReportCreation;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportsGenerationController {

    private final CommandGateway commandGateway;

    @Operation(summary = "Generation of report")
    @ApiResponse(responseCode = "200", description = "Report generated")
    @PostMapping("/generation")
    public ResponseEntity<Resource> generate(@Parameter(description = "Report type with params")
                                             @RequestBody ReportCreation report) {

        GeneratedFile generated = commandGateway.dispatch(
                new GenerateReportCommand(
                                report.getReportName(),
                                report.getSubsystem(),
                                report.getFormat(),
                                report.getParameters(),
                                report.getTitle(),
                                report.getDescription()));

        return ResponseEntity.ok()
                .headers(ReportHeadersFactory.generate(generated))
                .contentLength(generated.file().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(generated.file()));
    }

    @Operation(summary = "Group generation of reports")
    @ApiResponse(responseCode = "200", description = "Reports generated")
    @PostMapping("/group-generation")
    public ResponseEntity<Resource> groupGeneration(@Parameter(description = "Report type with params")
                                             @RequestBody List<ReportCreation> reports) throws Exception {

        List<GenerateReportCommand> commands = reports.stream()
                .map(report ->
                        new GenerateReportCommand(
                                report.getReportName(),
                                report.getSubsystem(),
                                report.getFormat(),
                                report.getParameters(),
                                report.getTitle(),
                                report.getDescription())).toList();

        List<GeneratedFile> generatedFiles = commandGateway.dispatchAll(commands);

        GeneratedFile pack = Zip.pack(generatedFiles);

        return ResponseEntity.ok()
                .headers(ReportHeadersFactory.generate(pack))
                .contentLength(pack.file().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(pack.file()));
    }

    @Operation(summary = "Scheduling generation of report")
    @ApiResponse(responseCode = "200", description = "Report scheduled for generation")
    @PostMapping("/scheduling")
    public void schedule(@Parameter(description = "Scheduled report with params") @RequestBody ScheduleReportCreation scheduledReport) {
        commandGateway.dispatch(
                new ScheduleReportGenerationCommand(
                        scheduledReport.getReportName(),
                        scheduledReport.getSubsystem(),
                        scheduledReport.getFormat(),
                        scheduledReport.getParameters(),
                        scheduledReport.getTitle(),
                        scheduledReport.getDescription(),
                        scheduledReport.getAt()));
        }

    @Operation(summary = "Downloading generated report")
    @ApiResponse(responseCode = "200", description = "Report downloaded")
    @GetMapping("/{reportId}")
    public ResponseEntity<Resource> download(@Parameter(description = "Identity of report") @PathVariable Long reportId) {

        GeneratedFile generated = commandGateway.dispatch(new DownloadReportCommand(reportId));

        return ResponseEntity.ok()
                .headers(ReportHeadersFactory.generate(generated))
                .contentLength(generated.file().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(generated.file()));
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class ReportHeadersFactory {

        static HttpHeaders generate(GeneratedFile report) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "%s.%s".formatted(report.fileName(),report.format()));
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return headers;
        }
    }
}
