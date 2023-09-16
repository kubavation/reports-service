package com.durys.jakub.reportsservice.pattern.infrastructure;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReportPatternRepository extends CrudRepository<ReportPattern, Long> {

    @Query("select p.file from ReportPattern p where p.name = :reportName and p.subsystem = :subsystem")
    Optional<byte[]> filePatternOf(String reportName, String subsystem);

}
