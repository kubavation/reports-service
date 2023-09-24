package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.api.model.ReportCreationParam;
import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.api.model.ReportFormat;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;

    public GeneratedReport generate(String reportName, String subsystem,
                                    Set<ReportCreationParam> reportParams, ReportFormat format) throws JRException {

        InputStream patternIS = reportPatternService.filePattern(reportName, subsystem);

        JasperReport report = ReportCache.compiledReport(patternIS)
                .getOrElseGet(r -> compile(patternIS));

        JasperPrint generated = ReportParametersService.fill(report, reportParams);

        return new GeneratedReport(
                ReportPrintService.print(generated, format),
                reportName,
                format.format());
    }


    private JasperReport compile(InputStream patternIS) {
        try {
            JasperReport report = JasperCompileManager.compileReport(patternIS);
            ReportCache.cache(report);
            return report;
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
