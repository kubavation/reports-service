package com.durys.jakub.reportsservice.report.api;

import com.durys.jakub.reportsservice.report.generator.ReportGenerator;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportGenerator reportGenerator;

    @PostMapping("/{subsystem}/{reportName}")
    public ResponseEntity<Resource> generate(@PathVariable String subsystem, @PathVariable String reportName) throws JRException {
        byte[] generated = reportGenerator.generate(reportName, subsystem);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(generated.length)
                .contentType(null) //todo
                .body(new ByteArrayResource(generated));
    }
}
