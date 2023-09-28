package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.api.model.ReportCreationParam;
import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.api.model.ReportFormat;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Path;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;
    private final DataSource dataSource;

    public GeneratedReport generate(String reportName, String subsystem,
                                    Set<ReportCreationParam> reportParams, ReportFormat format) throws Exception {

        Path filePath = reportPatternService.patternFilePath(reportName, subsystem);

        JasperReport report = ReportCache.compiledReport(filePath)
                .getOrElseGet(r -> compile(filePath));

        JasperPrint generated = ReportParametersService.fill(report, reportParams, dataSource);

        return new GeneratedReport(
                ReportPrintService.print(generated, format),
                reportName,
                format.format());
    }


    private JasperReport compile(Path filePath) {
        try {
            JasperReport report = JasperCompileManager.compileReport(filePath.toString());
            ReportCache.cache(report);
            return report;
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
