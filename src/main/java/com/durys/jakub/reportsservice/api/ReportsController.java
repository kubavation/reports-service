package com.durys.jakub.reportsservice.api;

import com.durys.jakub.reportsservice.api.model.ReportCreation;
import com.durys.jakub.reportsservice.api.model.ScheduleReportCreation;
import com.durys.jakub.reportsservice.report.application.ReportApplicationService;
import com.durys.jakub.reportsservice.generator.ReportGenerator;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import com.durys.jakub.reportsservice.scheduling.ReportScheduledGeneratorService;
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
public class ReportsController {

    private final ReportGenerator reportGenerator;
    private final ReportScheduledGeneratorService reportScheduledGeneratorService;
    private final ReportApplicationService reportApplicationService;

    @PostMapping("/generation")
    public ResponseEntity<Resource> generate(@RequestBody ReportCreation report) throws JRException {

        GeneratedReport generated = reportGenerator.generate(report.getReportName(), report.getSubsystem(),
                report.getParameters(), report.getFormat());

        return ResponseEntity.ok()
                .headers(ReportHeadersFactory.generate(generated))
                .contentLength(generated.file().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(generated.file()));
    }

    @PostMapping("/scheduling")
    public void schedule(@RequestBody ScheduleReportCreation scheduledReport) {
        reportScheduledGeneratorService.schedule(
                scheduledReport.getReportName(),
                scheduledReport.getSubsystem(),
                scheduledReport.getParameters(),
                scheduledReport.getFormat(),
                scheduledReport.getAt());
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<Resource> download(@PathVariable Long reportId) {

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
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=%s".formatted(report.fileName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return headers;
        }
    }
}
