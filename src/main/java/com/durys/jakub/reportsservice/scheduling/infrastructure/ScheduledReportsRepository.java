package com.durys.jakub.reportsservice.scheduling.infrastructure;

import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ScheduledReportsRepository extends JpaRepository<ScheduledReport, Long> {

    @Query(value = "SELECT sr.* FROM REP_SCHEDULED_REPORT sr where TO_CHAR(sr.at, 'yyyy-mm-dd HH24:MI') = :at ",
            nativeQuery = true)
    Set<ScheduledReport> findScheduled(LocalDateTime at);

    @Query("""
            select new com.durys.jakub.reportsservice.scheduling.infrastructure.ScheduledReportDTO(
            r.id, r.patternInformations.name, r.patternInformations.description, r.patternInformations.subsystem,
            r.fileName, r.status, sr.at)
           from Report r
           join ScheduledReport sr on sr.reportId = r.id
           """)
    List<ScheduledReportDTO> findAllScheduled();
}
