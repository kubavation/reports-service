package com.durys.jakub.reportsservice.report.application.event;

import com.durys.jakub.notificationclient.api.client.NotificationClient;
import com.durys.jakub.notificationclient.api.model.Notification;
import com.durys.jakub.notificationclient.api.model.NotificationType;
import com.durys.jakub.notificationclient.api.model.TenantId;
import com.durys.jakub.reportsservice.cqrs.command.CommandGateway;
import com.durys.jakub.reportsservice.event.EventPublisher;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.report.domain.command.GenerateReportCommand;
import com.durys.jakub.reportsservice.report.domain.event.ScheduledReportFaulitilyGeneratedEvent;
import com.durys.jakub.reportsservice.report.domain.event.ScheduledReportSuccessfullyGeneratedEvent;
import com.durys.jakub.reportsservice.report.infrastructure.in.model.ReportCreationParam;
import com.durys.jakub.reportsservice.scheduling.domain.event.GenerateScheduledReportEvent;
import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;
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
public class GenerateScheduledReportEventHandler {

    private final ReportRepository reportRepository;
    private final CommandGateway commandGateway;
    private final NotificationClient notificationClient;
    private final EventPublisher eventPublisher;

    @EventListener
    @Async
    public void handle(GenerateScheduledReportEvent event) {

        log.info("handling GenerateScheduledReportEvent (report_id: {})", event.reportId());

        Report report = reportRepository.find(event.reportId())
                .orElseThrow(RuntimeException::new);

        generate(report)
                .peekLeft(response -> eventPublisher.emit(new ScheduledReportFaulitilyGeneratedEvent(response.getId())))
                .peek(response -> eventPublisher.emit(new ScheduledReportSuccessfullyGeneratedEvent(response.getId())))
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

            GeneratedFile generated = commandGateway.dispatch(
                    new GenerateReportCommand(
                            report.getPatternInformations().getName(),
                            report.getPatternInformations().getSubsystem(),
                            report.getFormat(),
                            parameters,
                            report.getTitle(),
                            report.getDescription()));

            return Either.right(
                    reportRepository.save(
                            report.with(generated.fileName(), generated.file())));

        } catch (Exception e) {
            return Either.left(report);
        }
    }
}
