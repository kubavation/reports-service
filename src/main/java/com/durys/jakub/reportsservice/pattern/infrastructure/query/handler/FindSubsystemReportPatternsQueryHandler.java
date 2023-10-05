package com.durys.jakub.reportsservice.pattern.infrastructure.query.handler;

import com.durys.jakub.reportsservice.cqrs.query.QueryHandler;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandling;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternInfoDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.query.FindSubsystemReportPatternsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@QueryHandling
@Slf4j
@RequiredArgsConstructor
public class FindSubsystemReportPatternsQueryHandler implements QueryHandler<FindSubsystemReportPatternsQuery, Set<ReportPatternInfoDTO>> {

    private final ReportPatternRepository reportPatternRepository;

    @Override
    public Set<ReportPatternInfoDTO> handle(FindSubsystemReportPatternsQuery query) {
        log.info("Finding patterns for subsystem {}", query.subsystem());
        return reportPatternRepository.subsystemPatterns(query.subsystem());
    }
}
