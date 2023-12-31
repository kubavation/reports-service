package com.durys.jakub.reportsservice.scheduling.domain;

import com.durys.jakub.reportsservice.scheduling.infrastructure.ScheduledReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ScheduledReportsRepository extends JpaRepository<ScheduledReport, Long> {

    @Query(value = "SELECT sr.* FROM REP_SCHEDULED_REPORT sr " +
            "INNER JOIN REP_REPORT r on sr.report_id = r.id where TO_CHAR(sr.scheduled_at, 'yyyy-mm-dd') = :at ", nativeQuery = true)
    Set<ScheduledReport> findScheduled(Date at);

    @Query("""
            select new com.durys.jakub.reportsservice.scheduling.infrastructure.ScheduledReportDTO(
            r.id, r.patternInformations.name, r.patternInformations.description, r.patternInformations.subsystem,
            r.fileName, sr.status, sr.scheduleAt)
           from Report r
           join ScheduledReport sr on sr.reportId = r.id
           """)
    List<ScheduledReportDTO> findAllScheduled();

    Optional<ScheduledReport> findByReportId(Long reportId);
}
