package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.api.model.ReportCreationParam;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.util.Set;
import java.util.stream.Collectors;

class ReportParametersService {

    static JasperPrint fill(JasperReport report, Set<ReportCreationParam> params) throws JRException {

       var jasperParams = params.stream()
               .collect(Collectors.toMap(ReportCreationParam::getName, ReportCreationParam::getValue));

       return JasperFillManager.fillReport(report, jasperParams);
    }

}
