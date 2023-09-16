package com.durys.jakub.reportsservice.report.generator;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
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
                                    ReportParams reportParams, String format) throws JRException {

        InputStream patternIS = reportPatternService.pattern(reportName, subsystem);

        JasperReport report = JasperCompileManager.compileReport(patternIS);

        //todo report data

        JasperPrint generated = ReportParametersService.fill(report, reportParams.getValue());

        //todo generate

        byte[] result = JasperExportManager.exportReportToPdf(generated);

        return new GeneratedReport(result, reportName, "PDF"); //todo
    }

}
