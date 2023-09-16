package com.durys.jakub.reportsservice.report.api;

import com.durys.jakub.reportsservice.report.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.api.model.ReportParams;
import com.durys.jakub.reportsservice.report.generator.ReportGenerator;
import com.durys.jakub.reportsservice.report.generator.model.GeneratedReport;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportGenerator reportGenerator;

    @PostMapping("/generate/{subsystem}/{reportName}")
    public ResponseEntity<Resource> generate(@PathVariable String subsystem, @PathVariable String reportName,
                                             @RequestParam(name = "format", value = "PDF", required = false) ReportFormat format,
                                             @RequestBody ReportParams params) throws JRException {

        GeneratedReport generated = reportGenerator.generate(reportName, subsystem, params, format);

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
