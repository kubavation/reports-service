package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.api.model.ReportCreation;
import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;
    private final ReportParametersService reportParametersService;

    @SneakyThrows
    public GeneratedReport generate(ReportCreation report) {

        log.info("generating report {} for subsystem {}", report.getReportName(), report.getSubsystem());

        ReportPatternInfo patternInfo = reportPatternService.reportPatternInfo( report.getReportName(), report.getSubsystem());
        Path filePath = reportPatternService.patternFilePath(report.getReportName(), report.getSubsystem());

        JasperReport jasperReport = ReportCache.compiledReport(filePath)
                .getOrElseGet(r -> compile(filePath));

        JasperPrint generated = reportParametersService.fill(jasperReport, report.getParameters(), patternInfo);

        return new GeneratedReport(
                ReportPrintService.print(generated, report.getFormat()),
                report.getReportName(), report.getFormat().format());

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
