package com.durys.jakub.reportsservice.scheduling.domain;

import com.durys.jakub.reportsservice.event.EventPublisher;
import com.durys.jakub.reportsservice.scheduling.domain.event.GenerateScheduledReportEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "reports.scheduling", name = "enabled", havingValue = "true")
class ScheduledReportsService {

    private final ScheduledReportsRepository scheduledReportsRepository;
    private final EventPublisher eventPublisher;

    @Scheduled(cron = "${reports.scheduling.cron: * */30 * * * *}")
    @Async
    public void generateScheduledReports() {

      final LocalDateTime now = LocalDateTime.now();

      log.info("Scheduling reports at {}", now);

      Set<ScheduledReport> scheduled = scheduledReportsRepository.findScheduled(java.sql.Date.valueOf(LocalDate.now()))
              .stream()
              .filter(scheduledReport -> !scheduledReport.getScheduleAt().isAfter(now))
              .map(ScheduledReport::markAsStarted)
              .collect(Collectors.toSet());

      log.info("Scheduled reports count {}", scheduled.size());

      scheduledReportsRepository.saveAll(scheduled);

      scheduled.stream()
              .forEach(scheduledReport -> eventPublisher.emit(
                      new GenerateScheduledReportEvent(scheduledReport.getReportId())));

    }

}
