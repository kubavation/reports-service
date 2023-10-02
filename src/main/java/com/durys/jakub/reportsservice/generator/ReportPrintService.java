package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.report.infrastructure.model.ReportFormat;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

class ReportPrintService {

    static byte[] print(JasperPrint report, ReportFormat format) throws JRException {
        return switch (format) {
            case PDF -> JasperExportManager.exportReportToPdf(report);
        };
    }

}
