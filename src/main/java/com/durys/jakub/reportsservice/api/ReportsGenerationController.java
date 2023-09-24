package com.durys.jakub.reportsservice.api;

import com.durys.jakub.reportsservice.api.model.ReportCreation;
import com.durys.jakub.reportsservice.api.model.ScheduleReportCreation;
import com.durys.jakub.reportsservice.report.application.ReportApplicationService;
import com.durys.jakub.reportsservice.generator.ReportGenerator;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import com.durys.jakub.reportsservice.scheduling.ReportScheduledGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportsGenerationController {

    private final ReportGenerator reportGenerator;
    private final ReportScheduledGeneratorService reportScheduledGeneratorService;
    private final ReportApplicationService reportApplicationService;

    @Operation(summary = "Generation of report")
    @ApiResponse(responseCode = "200", description = "Report generated")
    @PostMapping("/generation")
    public ResponseEntity<Resource> generate(@Parameter(description = "Report type with params")
                                             @RequestBody ReportCreation report) throws JRException {

        GeneratedReport generated = reportGenerator.generate(report.getReportName(), report.getSubsystem(),
                report.getParameters(), report.getFormat());

        return ResponseEntity.ok()
                .headers(ReportHeadersFactory.generate(generated))
                .contentLength(generated.file().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(generated.file()));
    }

    @Operation(summary = "Scheduling generation of report")
    @ApiResponse(responseCode = "200", description = "Report scheduled for generation")
    @PostMapping("/scheduling")
    public void schedule(@Parameter(description = "Scheduled report with params") @RequestBody ScheduleReportCreation scheduledReport) {
        reportScheduledGeneratorService.schedule(
                scheduledReport.getReportName(),
                scheduledReport.getSubsystem(),
                scheduledReport.getParameters(),
                scheduledReport.getFormat(),
                scheduledReport.getAt());
    }

    @Operation(summary = "Downloading generated report")
    @ApiResponse(responseCode = "200", description = "Report downloaded")
    @GetMapping("/{reportId}")
    public ResponseEntity<Resource> download(@Parameter(description = "Identity of report") @PathVariable Long reportId) {

        GeneratedReport generated = reportApplicationService.download(reportId);

        return ResponseEntity.ok()
                .headers(ReportHeadersFactory.generate(generated))
                .contentLength(generated.file().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(generated.file()));
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class ReportHeadersFactory {

        static HttpHeaders generate(GeneratedReport report) {
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
