package com.durys.jakub.reportsservice.pattern.domain;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameterDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternInfoDTO;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ReportPatternRepository extends CrudRepository<ReportPattern, Long> {


    @Query(""" 
           select new com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo(
           p.informations.name, p.informations.description, p.informations.subsystem, p.informations.generationType)
           from ReportPattern p where p.informations.name = :reportName and p.informations.subsystem = :subsystem
           """)
    Optional<ReportPatternInfo> patternInformations(String reportName, String subsystem);


    @Query(""" 
           select new com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternInfoDTO(
           p.id, p.informations.name, p.informations.description, p.informations.subsystem, p.patternFile.fileName)
           from ReportPattern p where p.informations.subsystem = :subsystem
           """)
    Set<ReportPatternInfoDTO> subsystemPatterns(String subsystem);

    @Query(""" 
           select new com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameterDTO(p.name, p.type)
           from ReportPatternParameter p where p.pattern.id = :patternId
           """)
    Set<PatternParameterDTO> patternParams(Long patternId);

    @Query("SELECT rp from ReportPattern rp where rp.informations.subsystem = :subsystem and rp.informations.name = :reportName")
    Optional<ReportPattern> findBy(String subsystem, String reportName);

}
