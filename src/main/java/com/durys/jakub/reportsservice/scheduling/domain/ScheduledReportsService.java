package com.durys.jakub.reportsservice.scheduling;

import com.durys.jakub.reportsservice.event.EventPublisher;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReport;
import com.durys.jakub.reportsservice.scheduling.domain.event.GenerateScheduledReportEvent;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "reports.scheduling", name = "enabled", havingValue = "true")
public class ScheduledReportsService {

    private final ScheduledReportsRepository scheduledReportsRepository;
    private final EventPublisher eventPublisher;

    @Scheduled(cron = "${reports.scheduling.cron: * */30 * * * *}")
    @Async
    public void generateScheduledReports() {

      log.info("Scheduling reports at {}", LocalDateTime.now());

      Set<ScheduledReport> scheduled = scheduledReportsRepository.findScheduled(LocalDateTime.now());

      log.info("Scheduled reports count {}", scheduled.size());

      scheduled.stream()
              .forEach(scheduledReport -> eventPublisher.emit(
                      new GenerateScheduledReportEvent(scheduledReport.getReportId())));

    }

}
