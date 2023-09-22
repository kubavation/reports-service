package com.durys.jakub.reportsservice.scheduling;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
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
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportScheduledGeneratorService {

    private final ReportRepository reportRepository;
    private final ScheduledReportsRepository scheduledReportsRepository;
    private final ReportPatternApplicationService reportPatternService;

    @Transactional
    public void schedule(String reportName, String subsystem, Map<String, Object> reportParams,
                         ReportFormat format, LocalDateTime at) {

        ReportPatternInfo pattern = reportPatternService.reportPatternInfo(reportName, subsystem);

        Report report = reportRepository.save(
                Report.instanceOf(pattern, format.format(), UUID.randomUUID()) //todo get from token
                        .withParameters(reportParams)
        );

        scheduledReportsRepository.save(new ScheduledReport(report.getId(), at));
    }
}