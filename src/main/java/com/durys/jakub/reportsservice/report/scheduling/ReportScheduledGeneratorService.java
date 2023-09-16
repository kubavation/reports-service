package com.durys.jakub.reportsservice.report.scheduling;

import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo;
import com.durys.jakub.reportsservice.report.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.api.model.ReportParams;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.report.generator.model.GeneratedReport;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportScheduledGeneratorService {

    private final ReportRepository reportRepository;
    private final ReportPatternApplicationService reportPatternService;

    @Transactional
    public void generate(String reportName, String subsystem,
                                    ReportParams reportParams, ReportFormat format) {

        ReportPatternInfo pattern = reportPatternService.reportPatternInfo(reportName, subsystem);

        Report report = reportRepository.save(Report.instance());

    }
}
