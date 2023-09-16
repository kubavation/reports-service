package com.durys.jakub.reportsservice.report.generator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.util.Map;

class ReportParametersService {

    static JasperPrint fill(JasperReport report, Map<String, Object> params) throws JRException {
       return JasperFillManager.fillReport(report, params);
    }

}
