package com.durys.jakub.reportsservice.report.generator;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.report.api.ReportParams;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;

    public byte[] generate(String reportName, String subsystem, ReportParams reportParams) throws JRException {

        InputStream patternIS = reportPatternService.pattern(reportName, subsystem);

        JasperReport report = JasperCompileManager.compileReport(patternIS);

        //todo report data

        JasperPrint generated = ReportParametersService.fill(report, reportParams.getValue());

        //todo generate

        byte[] result = JasperExportManager.exportReportToPdf(generated);

        return result;
    }

}
