package com.durys.jakub.reportsservice.scheduling;

import com.durys.jakub.reportsservice.api.model.ReportCreationParam;
import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.report.domain.ReportParameter;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReport;
import com.durys.jakub.reportsservice.scheduling.infrastructure.ScheduledReportsRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import com.durys.jakub.reportsservice.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportScheduledGeneratorService {

    private final ReportRepository reportRepository;
    private final ScheduledReportsRepository scheduledReportsRepository;
    private final ReportPatternApplicationService reportPatternService;

    @Transactional
    public void schedule(String reportName, String subsystem, Set<ReportCreationParam> reportParams,
                         ReportFormat format, LocalDateTime at) {

        ReportPatternInfo pattern = reportPatternService.reportPatternInfo(reportName, subsystem);

        var parameters = reportParams.stream()
                .map(param -> new ReportParameter(param.getName(), param.getValue()))
                .collect(Collectors.toSet());

        Report report = reportRepository.save(
                Report.instanceOf(pattern, format.name(), UUID.randomUUID()) //todo get from token
                        .withParameters(parameters)
        );

        scheduledReportsRepository.save(new ScheduledReport(report.getId(), at));
    }
}
