package com.durys.jakub.reportsservice.bundles.application.handler;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundle;
import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.domain.command.DeleteReportBundleCommand;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CommandHandling
@Slf4j
@RequiredArgsConstructor
public class DeleteReportBundleCommandHandler implements CommandHandler<DeleteReportBundleCommand, Void> {

    private final ReportBundleRepository reportBundleRepository;

    @Override
    public Void handle(DeleteReportBundleCommand command) {
        log.info("delete report bundle (ID: {})", command.bundleId());

        ReportBundle reportBundle = reportBundleRepository.findById(command.bundleId())
                .map(ReportBundle::markAsDeleted)
                .orElseThrow(RuntimeException::new);

        reportBundleRepository.save(reportBundle);
        return null;
    }
}
