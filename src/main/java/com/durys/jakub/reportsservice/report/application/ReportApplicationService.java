package com.durys.jakub.reportsservice.report.application;

import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportParameter;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportApplicationService {

    private final ReportRepository reportRepository;


    public GeneratedReport download(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(RuntimeException::new);
        return new GeneratedReport(report.getFile(), report.getFileName(), report.getFormat());
    }


}
