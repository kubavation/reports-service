package com.durys.jakub.reportsservice.bundles.application.handler;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundle;
import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.domain.command.CreateReportBundleCommand;
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
public class CreateReportBundleCommandHandler implements CommandHandler<CreateReportBundleCommand, Void> {

    private final ReportRepository reportRepository;
    private final ReportBundleRepository reportBundleRepository;


    @Override
    public Void handle(CreateReportBundleCommand command) {

        log.info("creating report bundle with name {}", command.name());

        List<Report> reports = reportRepository.findAllById(command.reportIds());

        ReportBundle reportBundle = new ReportBundle(command.name(), Set.copyOf(reports));

        reportBundleRepository.save(reportBundle);
        return null;
    }
}
