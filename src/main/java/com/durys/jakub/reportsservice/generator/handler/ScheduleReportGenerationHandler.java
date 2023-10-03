package com.durys.jakub.reportsservice.generator.handler;

import com.durys.jakub.notificationclient.api.client.NotificationClient;
import com.durys.jakub.notificationclient.api.model.Notification;
import com.durys.jakub.notificationclient.api.model.NotificationType;
import com.durys.jakub.notificationclient.api.model.TenantId;
import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreation;
import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreationParam;
import com.durys.jakub.reportsservice.report.domain.ReportFormat;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.generator.ReportGenerator;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedReport;
import com.durys.jakub.reportsservice.scheduling.domain.event.GenerateScheduledReportEvent;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleReportGenerationHandler {

    private final ReportGenerator reportGenerator;
    private final ReportRepository reportRepository;
    private final NotificationClient notificationClient;

    @EventListener
    @Async
    public void handle(GenerateScheduledReportEvent event) {

        log.info("handling GenerateScheduledReportEvent (report_id: {})", event.reportId());

        Report report = reportRepository.find(event.reportId())
                .orElseThrow(RuntimeException::new);


        generate(report)
                .peek(rep -> notificationClient.send(
                        new Notification(new TenantId(rep.getTenantId().toString()),
                        "Report successfully created", "Report successfully created", List.of(NotificationType.APP))))
                .orElseRun(rep ->  notificationClient.send(
                        new Notification(new TenantId(rep.getTenantId().toString()),
                                "Report failed to create", "Report failed to create", List.of(NotificationType.APP))));

    }

    private Either<Report, Report> generate(Report report) {
        try {

            var parameters = report.getParameters()
                    .stream()
                    .map(param -> new ReportCreationParam(param.getName(), param.getValue()))
                    .collect(Collectors.toSet());

            var createReport = ReportCreation.builder()
                    .subsystem(report.getPatternInformations().getSubsystem())
                    .reportName(report.getPatternInformations().getName())
                    .parameters(parameters)
                    .format(ReportFormat.valueOf(report.getFormat()))
                    .title(report.getTitle())
                    .description(report.getDescription())
                    .build();

            GeneratedReport generated = reportGenerator.generate(createReport);

            return Either.right(reportRepository.save(report.with(generated.fileName(), generated.file()).markAsSucceeded()));
        } catch (Exception e) {
            return Either.left(reportRepository.save(report.markAsFailed()));
        }
    }
}
