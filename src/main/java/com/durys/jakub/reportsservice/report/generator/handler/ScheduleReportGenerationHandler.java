package com.durys.jakub.reportsservice.report.generator.handler;

import com.durys.jakub.notificationclient.api.client.NotificationClient;
import com.durys.jakub.notificationclient.api.model.Notification;
import com.durys.jakub.notificationclient.api.model.NotificationType;
import com.durys.jakub.notificationclient.api.model.TenantId;
import com.durys.jakub.reportsservice.notification.Notifications;
import com.durys.jakub.reportsservice.report.api.model.ReportFormat;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.report.generator.ReportGenerator;
import com.durys.jakub.reportsservice.report.generator.model.GeneratedReport;
import com.durys.jakub.reportsservice.report.scheduling.event.GenerateScheduledReportEvent;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleReportGenerationHandler {

    private final ReportGenerator reportGenerator;
    private final ReportRepository reportRepository;
    private final NotificationClient notificationClient;

    @EventListener
    @Async
    public void handle(GenerateScheduledReportEvent event) {

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
            final ReportPatternInfo info = report.getPatternInformations();
            GeneratedReport generated = reportGenerator.generate(info.getName(), info.getSubsystem(), report.params(), ReportFormat.valueOf(report.getFormat()));
            return Either.right(reportRepository.save(report.with(generated.fileName(), generated.file()).markAsSucceeded()));
        } catch (Exception e) {
            return Either.left(reportRepository.save(report.markAsFailed()));
        }
    }
}
