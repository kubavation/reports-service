package com.durys.jakub.reportsservice.common.generator;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.report.domain.ReportFormat;
import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreationParam;
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
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;
    private final ReportParametersService reportParametersService;

    @SneakyThrows
    public GeneratedReport generate(ReportPattern pattern, ReportFormat format, Set<ReportCreationParam> parameters) {

        log.info("generating report {} for subsystem {}", pattern.name(), pattern.subsystem());

        ReportPatternInfo patternInfo = reportPatternService.reportPatternInfo(pattern.name(), pattern.subsystem());
        Path filePath = reportPatternService.patternFilePath(pattern.name(), pattern.subsystem());

        JasperReport jasperReport = ReportCache.compiledReport(filePath)
                .getOrElseGet(r -> compile(filePath));

        JasperPrint generated = reportParametersService.fill(jasperReport, parameters, patternInfo);

        return new GeneratedReport(ReportPrintService.print(generated, format), pattern.name(), format.format());

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
