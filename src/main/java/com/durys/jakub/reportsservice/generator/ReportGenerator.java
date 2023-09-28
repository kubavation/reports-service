package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.api.model.ReportCreationParam;
import com.durys.jakub.reportsservice.api.model.ReportFormat;
import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;
    private final ReportParametersService reportParametersService;

    public GeneratedReport generate(String reportName, String subsystem,
                                    Set<ReportCreationParam> reportParams, ReportFormat format) throws Exception {

        log.info("generating report {} for subsystem {}", reportName, subsystem);

        ReportPatternInfo patternInfo = reportPatternService.reportPatternInfo(reportName, subsystem);
        Path filePath = reportPatternService.patternFilePath(reportName, subsystem);

        JasperReport report = ReportCache.compiledReport(filePath)
                .getOrElseGet(r -> compile(filePath));

        JasperPrint generated = reportParametersService.fill(report, reportParams, patternInfo);

        return new GeneratedReport(
                ReportPrintService.print(generated, format),
                reportName, format.format());
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
