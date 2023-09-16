package com.durys.jakub.reportsservice.report.generator.handler;

import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import com.durys.jakub.reportsservice.report.generator.ReportGenerator;
import com.durys.jakub.reportsservice.report.generator.model.GeneratedReport;
import com.durys.jakub.reportsservice.report.scheduling.event.ScheduleReportGenerationEvent;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleReportGenerationHandler {

    private final ReportGenerator reportGenerator;
    private final ReportRepository reportRepository;

    @EventListener
    @Async
    public void handle(ScheduleReportGenerationEvent event) {

        Report report = reportRepository.findById(event.reportId())
                .orElseThrow(RuntimeException::new);

        generate(report, event)
                .peek(rep -> System.out.println("todo"))
                .orElseRun(rep -> System.out.println("todo")); //todo notifications

    }

    private Either<Report, Report> generate(Report report, ScheduleReportGenerationEvent event) {
        try {
            GeneratedReport generated = reportGenerator.generate(event.reportName(), event.subsystem(), event.params(), event.format());
            return Either.right(reportRepository.save(report.with(generated.fileName(), generated.file()).markAsSucceeded()));
        } catch (Exception e) {
            return Either.left(reportRepository.save(report.markAsFailed()));
        }
    }
}
