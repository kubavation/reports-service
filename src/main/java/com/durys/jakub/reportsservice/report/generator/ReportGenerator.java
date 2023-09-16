package com.durys.jakub.reportsservice.report.generator;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.report.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.api.model.ReportParams;
import com.durys.jakub.reportsservice.report.generator.model.GeneratedReport;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;

    public GeneratedReport generate(String reportName, String subsystem,
                                    ReportParams reportParams, ReportFormat format) throws JRException {

        InputStream patternIS = reportPatternService.filePattern(reportName, subsystem);

        JasperReport report = JasperCompileManager.compileReport(patternIS);

        JasperPrint generated = ReportParametersService.fill(report, reportParams.getValue());

        byte[] result = ReportPrintService.print(generated, format);

        return new GeneratedReport(result, reportName, format.format());
    }

}
