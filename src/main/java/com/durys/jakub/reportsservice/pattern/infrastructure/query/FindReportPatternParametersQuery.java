package com.durys.jakub.reportsservice.pattern.infrastructure.query;

import com.durys.jakub.reportsservice.cqrs.query.Query;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.PatternParameterDTO;

import java.util.Set;

public record FindReportPatternParametersQuery(Long patternId) implements Query<Set<PatternParameterDTO>> {
}
