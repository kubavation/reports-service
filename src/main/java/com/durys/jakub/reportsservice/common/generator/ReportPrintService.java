package com.durys.jakub.reportsservice.common.generator;

import com.durys.jakub.reportsservice.report.domain.ReportFormat;
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
