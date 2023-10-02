package com.durys.jakub.reportsservice.report.application;

import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportApplicationService {

    private final ReportRepository reportRepository;

    public GeneratedReport find(Long reportId) {
        return reportRepository.findById(reportId)
                .map(report -> new GeneratedReport(report.getFile(), report.getFileName(), report.getFormat()))
                .orElseThrow(RuntimeException::new);
    }


}
