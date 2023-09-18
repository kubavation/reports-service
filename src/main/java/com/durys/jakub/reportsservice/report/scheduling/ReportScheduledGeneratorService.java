package com.durys.jakub.reportsservice.report.scheduling;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import com.durys.jakub.reportsservice.report.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.report.scheduling.domain.ScheduledReport;
import com.durys.jakub.reportsservice.report.scheduling.infrastructure.ScheduledReportsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

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
                Report.instanceOf(pattern, format.format())
                        .withParameters(reportParams)
        );

        scheduledReportsRepository.save(new ScheduledReport(report.getId(), at));
    }
}
