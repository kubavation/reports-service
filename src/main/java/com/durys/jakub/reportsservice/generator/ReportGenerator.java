package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.api.model.ReportFormat;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReportGenerator {

    private final ReportPatternApplicationService reportPatternService;

    public GeneratedReport generate(String reportName, String subsystem,
                                    Map<String, Object> reportParams, ReportFormat format) throws JRException {

        InputStream patternIS = reportPatternService.filePattern(reportName, subsystem);

        JasperReport report = JasperCompileManager.compileReport(patternIS);

        JasperPrint generated = ReportParametersService.fill(report, reportParams);

        byte[] result = ReportPrintService.print(generated, format);

        return new GeneratedReport(result, reportName, format.format());
    }

}
