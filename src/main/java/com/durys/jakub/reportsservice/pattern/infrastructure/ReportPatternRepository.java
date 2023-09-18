package com.durys.jakub.reportsservice.pattern.infrastructure;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternParameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ReportPatternRepository extends CrudRepository<ReportPattern, Long> {

    @Query("select p.file from ReportPattern p where p.informations.name = :reportName and p.informations.subsystem = :subsystem")
    Optional<byte[]> filePatternOf(String reportName, String subsystem);

    @Query(""" 
           select new com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo(p.informations.name, p.informations.description, p.informations.subsystem)
           from ReportPattern p where p.informations.name = :reportName and p.informations.subsystem = :subsystem
           """)
    Optional<ReportPatternInfo> patternInformations(String reportName, String subsystem);

    @Query("from ReportPatternParameter param where param.pattern.id = :patternId")
    Set<ReportPatternParameter> patternParameters(Long patternId);
}
