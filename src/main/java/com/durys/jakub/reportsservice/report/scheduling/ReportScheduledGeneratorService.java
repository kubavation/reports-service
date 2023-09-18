package com.durys.jakub.reportsservice.report.scheduling;

import com.durys.jakub.reportsservice.event.EventPublisher;
import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo;
import com.durys.jakub.reportsservice.report.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.report.scheduling.event.ScheduleReportGenerationEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportScheduledGeneratorService {

    private final ReportRepository reportRepository;
    private final ReportPatternApplicationService reportPatternService;
    private final EventPublisher eventPublisher;

    @Transactional
    public void schedule(String reportName, String subsystem,
                         Map<String, Object> reportParams, ReportFormat format) {

        ReportPatternInfo pattern = reportPatternService.reportPatternInfo(reportName, subsystem);

        Report report = reportRepository.save(Report.instance(pattern.getName(), pattern.getSubsystem()));

        eventPublisher.emit(new ScheduleReportGenerationEvent(report.getId(), reportName, subsystem, reportParams, format));
    }
}
