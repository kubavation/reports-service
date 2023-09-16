package com.durys.jakub.reportsservice.report.api;

import com.durys.jakub.reportsservice.report.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.api.model.ReportParams;
import com.durys.jakub.reportsservice.report.generator.ReportGenerator;
import com.durys.jakub.reportsservice.report.generator.model.GeneratedReport;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportGenerator reportGenerator;

    @PostMapping("/{subsystem}/{reportName}")
    public ResponseEntity<Resource> generate(@PathVariable String subsystem, @PathVariable String reportName,
                                             @RequestParam(name = "format", value = "PDF") ReportFormat format,
                                             @RequestBody ReportParams params) throws JRException {

        GeneratedReport generated = reportGenerator.generate(reportName, subsystem, params, format);

        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(generated.file().length)
                .contentType(null) //todo
                .body(new ByteArrayResource(generated.file()));
    }
}
