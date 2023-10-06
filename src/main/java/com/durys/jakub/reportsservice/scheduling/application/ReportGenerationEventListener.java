package com.durys.jakub.reportsservice.scheduling.application;

import com.durys.jakub.reportsservice.report.domain.event.ScheduledReportFaulitilyGeneratedEvent;
import com.durys.jakub.reportsservice.report.domain.event.ScheduledReportSuccessfullyGeneratedEvent;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReport;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportGenerationEventListener {

    private final ScheduledReportsRepository scheduledReportsRepository;

    @EventListener
    public void handle(ScheduledReportSuccessfullyGeneratedEvent event) {

        log.info("handling ScheduledReportSuccessfullyGeneratedEvent");

        scheduledReportsRepository.findByReportId(event.reportId())
                .map(ScheduledReport::markAsGenerated)
                .ifPresent(scheduledReportsRepository::save);

    }

    @EventListener
    public void handle(ScheduledReportFaulitilyGeneratedEvent event) {

        log.info("handling ScheduledReportFaulitilyGeneratedEvent");

        scheduledReportsRepository.findByReportId(event.reportId())
                .map(ScheduledReport::markAsFailed)
                .ifPresent(scheduledReportsRepository::save);

    }

}
