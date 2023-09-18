package com.durys.jakub.reportsservice.report.scheduling.infrastructure;

import com.durys.jakub.reportsservice.report.scheduling.domain.ScheduledReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledReportsRepository extends JpaRepository<ScheduledReport, Long> {
}
