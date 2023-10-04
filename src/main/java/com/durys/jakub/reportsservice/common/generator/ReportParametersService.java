package com.durys.jakub.reportsservice.common.generator;

import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreationParam;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class ReportParametersService {

    private final ConnectionProvider connectionProvider;

    JasperPrint fill(JasperReport report, Set<ReportCreationParam> params, ReportPatternInfo pattern) throws JRException {

        var jasperParams = convertToJasperParameters(params);

        if (pattern.dbGeneration()) {
            return JasperFillManager.fillReport(report, jasperParams, connectionProvider.obtainConnection(pattern));
        }

        return JasperFillManager.fillReport(report, jasperParams, new JREmptyDataSource());
    }

    private static Map<String, Object> convertToJasperParameters(Set<ReportCreationParam> parameters) {
        return parameters.stream()
                .collect(Collectors.toMap(ReportCreationParam::getName, ReportCreationParam::getValue));
    }

}
