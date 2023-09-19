package com.durys.jakub.reportsservice.pattern.infrastructure;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameter;
import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternParameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ReportPatternRepository extends CrudRepository<ReportPattern, Long> {

    @Query("select p.file from ReportPattern p where p.informations.name = :reportName and p.informations.subsystem = :subsystem")
    Optional<byte[]> filePatternOf(String reportName, String subsystem);

    @Query(""" 
           select new com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo(p.informations.name, p.informations.description, p.informations.subsystem)
           from ReportPattern p where p.informations.name = :reportName and p.informations.subsystem = :subsystem
           """)
    Optional<ReportPatternInfo> patternInformations(String reportName, String subsystem);

    @Query("from ReportPatternParameter param where param.pattern.id = :patternId")
    Set<ReportPatternParameter> patternParameters(Long patternId);

    @Query(""" 
           select new com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo(p.informations.name, p.informations.description, p.informations.subsystem)
           from ReportPattern p where p.informations.subsystem = :subsystem
           """)
    Set<ReportPatternInfo> subsystemPatterns(String subsystem);

    @Query(""" 
           select new com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameter(p.name, p.type)
           from ReportPatternParameter p where p.pattern.id = :patternId
           """)
    Set<PatternParameter> patternParams(Long patternId);
}
