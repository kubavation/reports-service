package com.durys.jakub.reportsservice.scheduling;

import com.durys.jakub.reportsservice.api.model.ScheduleReportCreation;
import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportParameter;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReport;
import com.durys.jakub.reportsservice.scheduling.infrastructure.ScheduledReportsRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportScheduledGeneratorService {

    private final ReportRepository reportRepository;
    private final ScheduledReportsRepository scheduledReportsRepository;
    private final ReportPatternApplicationService reportPatternService;


    @Transactional
    public void schedule(ScheduleReportCreation report) {

        ReportPatternInfo pattern = reportPatternService.reportPatternInfo(report.getReportName(), report.getSubsystem());

        var parameters = report.getParameters().stream()
                .map(param -> new ReportParameter(param.getName(), param.getValue()))
                .collect(Collectors.toSet());

        Report scheduleReport = reportRepository.save(
                Report.instanceOf(pattern, report.getFormat().name(), UUID.randomUUID(), report.getTitle(), report.getDescription())
                        .withParameters(parameters)
        );

        scheduledReportsRepository.save(new ScheduledReport(scheduleReport.getId(), report.getAt()));

    }
}
