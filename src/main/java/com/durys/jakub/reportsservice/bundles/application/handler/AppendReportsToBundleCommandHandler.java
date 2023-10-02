package com.durys.jakub.reportsservice.bundles.application.handler;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundle;
import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.domain.command.AppendReportsToBundleCommand;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@CommandHandling
@Slf4j
@RequiredArgsConstructor
public class AppendReportsToBundleCommandHandler implements CommandHandler<AppendReportsToBundleCommand, Void> {

    private final ReportRepository reportRepository;
    private final ReportBundleRepository reportBundleRepository;


    @Override
    public Void handle(AppendReportsToBundleCommand command) {
        log.info("append report bundle (ID: {})", command.bundleId());

        ReportBundle reportBundle = reportBundleRepository.findById(command.bundleId())
                .orElseThrow(RuntimeException::new);

        List<Report> reports = reportRepository.findAllById(command.reportIds());

        reportBundle.append(Set.copyOf(reports));
        return null;
    }
}
