package com.durys.jakub.reportsservice.pattern.infrastructure.query.handler;

import com.durys.jakub.reportsservice.cqrs.query.QueryHandler;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandling;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternRepository;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameterDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternInfoDTO;
import com.durys.jakub.reportsservice.pattern.infrastructure.query.FindReportPatternParametersQuery;
import com.durys.jakub.reportsservice.pattern.infrastructure.query.FindSubsystemReportPatternsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@QueryHandling
@Slf4j
@RequiredArgsConstructor
public class FindReportPatternParametersQueryHandler implements QueryHandler<FindReportPatternParametersQuery, Set<PatternParameterDTO>> {

    private final ReportPatternRepository reportPatternRepository;

    @Override
    public Set<PatternParameterDTO> handle(FindReportPatternParametersQuery query) {
        log.info("Finding parameters for pattern {}", query.patternId());
        return reportPatternRepository.patternParams(query.patternId());
    }
}
