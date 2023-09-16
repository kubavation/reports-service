package com.durys.jakub.reportsservice.report.generator;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;

    public byte[] generate(String reportName, String subsystem) throws JRException {

        InputStream patternIS = reportPatternService.pattern(reportName, subsystem);

        JasperReport report = JasperCompileManager.compileReport(patternIS);

        //todo report data

        JasperPrint generated = JasperFillManager.fillReport(report, new HashMap<>());

        //todo generate

        byte[] result = JasperExportManager.exportReportToPdf(generated);

        return result;
    }

}
