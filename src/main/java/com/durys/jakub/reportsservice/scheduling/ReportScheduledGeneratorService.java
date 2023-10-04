package com.durys.jakub.reportsservice.scheduling;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreationParam;
import com.durys.jakub.reportsservice.report.domain.ReportFormat;
import com.durys.jakub.reportsservice.pattern.application.ReportPatternApplicationService;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportParameter;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReport;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReportsRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
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

    @Transactional
    public void schedule(ReportPattern pattern, Set<ReportCreationParam> params, ReportFormat format,
                         String title, String description, LocalDateTime at) {

        var parameters = params.stream()
                .map(param -> new ReportParameter(param.getName(), param.getValue()))
                .collect(Collectors.toSet());

        Report report = reportRepository.save(
                Report.instanceOf(pattern, format, UUID.randomUUID(), title, description)
                        .withParameters(parameters));

        scheduledReportsRepository.save(new ScheduledReport(report.getId(), at));

    }
}
